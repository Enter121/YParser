package me.yaacob.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import me.yaacob.Expressions.out;
import me.yaacob.interpreter.Cell;
import me.yaacob.interpreter.Expression;
import me.yaacob.interpreter.Type;
import me.yaacob.interpreter.Variable;
import me.yaacob.interpreter.Void;

public class Interpreter {

	public HashMap<String,Cell> cells=new HashMap<>();
	public HashMap<String,Void> voids=new HashMap<>();
	public ArrayList<Expression> expressions = new ArrayList<>();
	public ArrayList<Type> types=new ArrayList<>();
	public ArrayList<Variable> variables=new ArrayList<>();
	
	public Interpreter(){
		this.registerExpression(new out());
		this.registerType(new Type(String.class));
		this.registerType(new Type(Float.class));
	}
	
	public void registerExpression(Expression e){
		expressions.add(e);
	}
	
	public void removeExpression(Expression e){
		expressions.remove(e);
	}
	
	public void registerType(Type e){
		types.add(e);
	}
	
	public void removeType(Type e){
		types.remove(e);
	}
	public void registerClass(Class c){
		this.removeType(new Type(c));
	}
	
	
	public void parse(String c){
		cells.clear();
		
		
		
		String lastID="";
		int id=0;
		int maincell=0;
		boolean quote=false;
		String content="";

		for(int i=0;i!=c.toCharArray().length;i++){
		Character cc=c.toCharArray()[i];
		boolean put=true;
		switch(cc){
		case ' ':
			put=quote;
		break;
		
		case '"':
		quote=!quote;
		break;
		
		}
		if(put){
			content+=cc;
		}
		}
		content=content.replace("@", "");
		for(int i=0;i!=content.toCharArray().length;i++){
		Character cc=content.toCharArray()[i];
		switch(cc){
		
		case '{':
			if(id==0){
				lastID=maincell+"";
				maincell++;
			}else{
				lastID+="-"+id;
			}
			cells.put(lastID, new Cell(lastID).setBegin(i));
			id++;
		break;
		
		case '}':
			
			cells.get(lastID).setEnd(i+1).generate(content);
			if(lastID.contains("-")){
				lastID=lastID.replace("-"+lastID.split("-")[lastID.split("-").length-1], "");

			}else{
				id=0;
			}
		break;
		
		}
		}
		for(Cell ce1 :cells.values()){
			for(Cell ce2 :cells.values()){
				if(!ce1.equals(ce2)){
					ce2.content=ce2.content.replace(ce1.content, "@"+ce1.id+";");
				}
				
			}
		content=content.replace(ce1.content, "@"+ce1.id+";");
			
		}
		
		for(Cell ce :cells.values()){
			ce.split();
		}
		
		String[] lines=content.split(";");
		for(String line :lines){
			prepare(line);
		}
		
		
		voids.get("main").make(this);
		
		
	}
	
	public void prepare(String line){
		if(line.startsWith("void") && line.contains("@"))voids.put(line.replace(line.split("@")[1], "").replace("void", "").replace("@", ""),new Void(line.replace(line.split("@")[1], "").replace("void", "").replace("@", ""),cells.get(line.split("@")[1])));
	}
	
	public void make(String line){
		if(line.startsWith("@")){
			cells.get(line.replace("@", "")).make(this);
		}
		
		
		for(Type t:types){
			line=line.replace("int", "Integer").replace("boolean", "Boolean").replace("float", "Float").replace("Double", "double").replaceAll("string", "String");
			if(line.contains(t.name)){
				
								String[] temp=line.replace(t.name, "").split("=");
				Object[] objects= new Object[temp[1].split(",").length];
				Class[] classes= new Class[objects.length];
				
				
				for(int i=0;i!=temp[1].split(",").length;i++){
					String s=temp[1].split(",")[i];
					
					objects[i]=s;
					for(Variable v:variables){
						if(s.equals(v.name)){
							objects[i]=v.value;
							
						}
						if(objects[i] instanceof String)objects[i]=((String)objects[i]).replace("+"+v.name+"+", v.value.toString()).replace("+"+v.name, v.value.toString()).replace(v.name+"+", v.value.toString());

					
					}
					
			
				}
				
				for(int i=0;i!=objects.length;i++){
				
					classes[i]=objects[i].getClass();
					
				}

				try {
					if(t.class_.getConstructor(classes)!=null){
						try {
							Variable var = new Variable(t,temp[0],t.class_.getConstructor(classes).newInstance(objects));
							this.variables.add(var);
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
//				t.class_.getConstructor(parameterTypes)
//				
//				
					
					
					break;
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		if(line.contains("(") && line.contains(")")){
			String name=line.split("\\(")[0];
			String[] args_ =line.split("\\(")[1].replace(")", "").split(",");
			Object[] args=new Object[args_.length];
			for(int i=0;i!=args_.length;i++){
				String arg=args_[i];
				
				args[i]=args_[i];
				for(Variable v:variables){
					if(arg.equals(v.name)){
						args[i]=v.value;
					
					}
				
						if(args[i] instanceof String)args[i]=((String)args[i]).replace("+"+v.name+"+", v.value.toString()).replace("+"+v.name, v.value.toString()).replace(v.name+"+", v.value.toString());
					
				}
				
			}
			
			for(Expression e:expressions){
				if(e.name.equals(name)){
			
					e.make(args);
					
				}
			}
			for(Void e:voids.values()){
				if(e.name.equals(name)){
					
					
					e.make(this);
					
				}
			}
			
			
		}
		
		
	}
	
}
