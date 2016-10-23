package me.yaacob.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import me.yaacob.Expressions._for;
import me.yaacob.Expressions._if;
import me.yaacob.Expressions._while;
import me.yaacob.Expressions.out;
import me.yaacob.Expressions.outln;
import me.yaacob.Objects.Table;
import me.yaacob.interpreter.Cell;
import me.yaacob.interpreter.Condition;
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
	public ArrayList<Variable> toAdd=new ArrayList<>();
	
	
	
	public Scanner input=new Scanner(System.in);
	
	public Interpreter(){
		
		
		
		this.registerExpression(new out());
		this.registerExpression(new outln());
		this.registerExpression(new _if());
		this.registerExpression(new _while());
		this.registerExpression(new _for());
		this.registerType(new Type(String.class));
		this.registerType(new Type(Float.class));
		this.registerType(new Type(Integer.class));
		this.registerType(new Type(Table.class));
		this.registerType(new Type(Object.class));
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
	ArrayList<Variable> todel=new ArrayList<>();
	public void removeVar(String name){
		
		for(Variable v:variables){
			if(v.name.equals(name)){
				
				todel.add(v);
			}
			
		}
		for(Variable v:todel){
			variables.remove(v);
		}
		todel.clear();
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
	public Condition lastCondition;
	public void make(String line){
		line=line.replace("int", "Integer").replace("boolean", "Boolean").replace("float", "Float").replace("Double", "double").replaceAll("string", "String");

		

		
		
		if(line.contains("Input.get()"))line=line.replace("Input.get()", input.nextLine());
		
		
		
		if(line.startsWith("@"))cells.get(line.replace("@", "")).make(this);
		
		
		
		for(Variable v:variables){
			
			
			if(line.contains("$"+v.name)){
				boolean b=false;
				String bl=line.split("\\$")[1];
				
				
				line=line.replace("$", "");
				if(line.contains(":")){
					
					String cmd=bl.split(":")[1];
					if(cmd.contains("(")){
						//cmd+=")";
						String[] a=null;
						if(cmd.split("\\(").length>1)a=cmd.split("\\(")[1].replace("(", "").replace(")", "").replace("\"", "").split(",");
						
						Object[] args=new Object[a.length];
						for(int i=0;i!=a.length;i++){
							args[i]=a[i];
						}
						
						for(int i=0;i!=args.length;i++){
							Object arg=args[i].toString();
							if(Utils.isNumeric(args[i].toString())){
							
								args[i]=Integer.parseInt(args[i].toString());
							}
							
							for(Variable va:variables){
								if(arg.equals(va.name)){
									args[i]=va.value;
								
								}
							
									if(args[i] instanceof String)args[i]=((String)args[i]).replace("+"+v.name+"+", v.value.toString()).replace("+"+v.name, v.value.toString()).replace(v.name+"+", v.value.toString());
								
							}
						}
					
						try {
							if(args.length==1 && args[0].equals("")){
								v.t.methods.get(cmd.split("\\(")[0]).invoke(v.value);

							}else{
							
								v.t.methods.get(cmd.split("\\(")[0]).invoke(v.value, args);
							}
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
						
					}else{
						
					
						
						
						if(line.contains("=") && line.split("=")[0].contains(cmd)){
							
							Object o=line.split("=")[1];
							String name=cmd;
							for(Variable var:variables){
								if(o.equals(var.name))o=var.value;
								
							}
							
							
							try {
								v.t.fields.get(cmd).set(v.value, o);
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}else{
							try {
								Type tt=null;
								for(Type t:types){
								
									if(t.class_.equals(v.t.fields.get(cmd.replace(")", "")).getType())){
										tt=t;
									}
								}
								
								String str="#_"+variables.size();
								
								toAdd.add(new Variable(tt,str,v.t.fields.get(cmd.replace(")", "")).get(v.value)));
							
								line=line.replace(bl , str);
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					
						
							
							
						}
						
						
						
					}
					
	
					
				}else{
					bl=bl.split(",")[0].split("\\+")[0].split("\\)")[0];
					line=line.replace("++", "+=1").replace("--", "-=1");
					String type="";
					if(line.toString().contains("+=")){
						type="+=";
					}else
						if(line.toString().contains("-=")){
							type="-=";
						}else
							if(line.toString().contains("*=")){
								type="*=";
							}else
								if(line.toString().contains("/=")){
									type="/=";
								}
					
					if(type!="")b=true;
					Object val=line.replace(v.name+type, "");
					boolean changed=false;
					for(Variable vv:variables){
						if(vv.name.equals(val.toString())){
							changed=true;
							val=vv.value;
						}
					}
					if(!changed){
						if(Utils.isNumeric(val.toString())){
							val=Float.parseFloat(val.toString());
						}
						
						
					}
					if(val instanceof String){
						for(Variable vvv:variables){
							val=((String)val).replace("+"+vvv.name+"+", vvv.value.toString()).replace("+"+vvv.name, vvv.value.toString()).replace(vvv.name+"+", vvv.value.toString());

						}
					}
					
					switch(type){
					
					case "+=":
						if(Utils.isNumeric(val.toString())){
							v.value=Float.parseFloat(v.value.toString())+Float.parseFloat(val.toString());
						}else{
							v.value=v.value.toString()+val;
						}
					break;
					case "-=":
						if(Utils.isNumeric(val.toString()))v.value=(float)v.value-Float.parseFloat(val.toString());
						break;
					case "*=":
						
						if(Utils.isNumeric(val.toString()))v.value=(float)v.value*Float.parseFloat(val.toString());
						break;
					case "/=":
						if(Utils.isNumeric(val.toString()))v.value=(float)v.value/Float.parseFloat(val.toString());
						break;
					}
		
				
				if(line.contains("=") && !b){
					Object o=line.split("=")[1];
					 changed=false;
					for(Variable vv:variables){
						if(vv.name.equals(o.toString())){
							changed=true;
							o=vv.value;
						}
					}
					if(!changed){
						if(Utils.isNumeric(o.toString())){
							o=Float.parseFloat(o.toString());
						}
						
						
					}
					
					v.value=o;
				}
				//v.value=;
				
			}
			}
			
		}
		
		for(Variable v:toAdd){
			variables.add(v);
		}
		toAdd.clear();
		
		
		
		
	
		
		
		
		if(lastCondition!=null){
		if(line.startsWith("else") && lastCondition.getNegate()){
			this.make(line.replace("else", ""));
		}
		}
		
		
		
		
		
		
		
		for(Type t:types){
			if(line.startsWith(t.name)){
			
				
			
				String[] temp=line.replace(t.name, "").split("=");
				
				Object[] objects= new Object[temp[1].split(",").length];
				Class[] classes= new Class[objects.length];
				
				
				if(temp[1].split(",").length==1 && temp[1].split(",")[0].equals("()")){
					Variable var = null;
					try {
						var = new Variable(t,temp[0],t.class_.newInstance());
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.variables.add(var);
				}else{
				
				
				
				for(int i=0;i!=temp[1].split(",").length;i++){
					Object s=temp[1].replace("(", "").replace(")", "").split(",")[i];
					
					
					
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
		}
		
		
		

		
		if(line.contains("(") && !line.startsWith("$")){
			
			
			String name=null;
			String[] args_=null;
			Object[] args = null;
			if(line.split("\\)")[0].split("\\(").length>1){
			name=line.split("\\(")[0];
			args_=line.split("\\)")[0].split("\\(")[1].replace(")", "").split(",");
			args=new Object[args_.length];
			
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
			
		}else{
			name=line.split("\\(")[0];
		}
		
			for(Expression e:expressions){
				if(e.name.equals(name)){
					
					e.make(this,line,args);
					
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
