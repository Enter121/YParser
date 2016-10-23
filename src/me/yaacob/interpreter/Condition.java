package me.yaacob.interpreter;

import me.yaacob.core.Interpreter;
import me.yaacob.core.Utils;

public class Condition {
	
	Object o1;
	Object o2;
	
	boolean value=false;
	
	public Condition(Interpreter i, Object o){
		
if(o.toString().contains("\\|\\|") ||o.toString().contains("&&")){
		
			
			String[] and;
			
			and=o.toString().split("&&");
			Condition[] con_and=new Condition[and.length];
			Condition[] con;
			for(int k=0;k!=and.length;k++){
				String str=and[k];
				
				String[] or=str.split("\\|\\|");
				con=new Condition[or.length];
				for(int j=0;j!=or.length;j++){
					con[j]=new Condition(i,or[j]);
				}
				
				con_and[k]=new Condition(ConditionType.OR,con);
			}
			Condition c=new Condition(ConditionType.AND,con_and);
		this.value=c.get();	
}else{
		
		
		String type="";
		if(o.toString().contains("==")){
			type="==";
		}else
			if(o.toString().contains("!=")){
				type="!=";
			}else
				if(o.toString().contains(">=")){
					type=">=";
				}else
					if(o.toString().contains("<=")){
						type="<=";
					}else
						if(o.toString().contains("<")){
							type="<";
						}else
							if(o.toString().contains(">")){
								type=">";
							}
		
		Object o1=o.toString().split(type)[0];
		Object o2=o.toString().split(type)[1];
		
		if(Utils.isNumeric(o1.toString())){
			o1=Float.parseFloat(o1.toString());
		}
		
		if(Utils.isNumeric(o2.toString())){
			o2=Float.parseFloat(o2.toString());
		}
		
		for(Variable v:i.variables){
			if((o1.toString()).equals(v.name) && !o1.toString().contains("\"")){
				o1=v.value;
			}
			if((o2.toString()).equals(v.name) && !o1.toString().contains("\"")){
				o2=v.value;
			}
			
			if(o1 instanceof String)o1=((String)o1).replace("+"+v.name+"+", v.value.toString()).replace("+"+v.name, v.value.toString()).replace(v.name+"+", v.value.toString());
			if(o2 instanceof String)o2=((String)o2).replace("+"+v.name+"+", v.value.toString()).replace("+"+v.name, v.value.toString()).replace(v.name+"+", v.value.toString());

		}
		
		switch(type){
		
		case "==":
			if(o1.equals(o2))this.value=true;
		break;
		case "!=":
			if(!o1.equals(o2))this.value=true;
		break;
		case ">=":
			if(Float.parseFloat(o1.toString())>=(Float.parseFloat(o2.toString())))this.value=true;
		break;
		case "<=":
			if((Float.parseFloat(o1.toString())<=(Float.parseFloat(o2.toString()))))this.value=true;
		break;
		case ">":
			if((Float.parseFloat(o1.toString())>(Float.parseFloat(o2.toString()))))this.value=true;
		break;
		case "<":
			if((Float.parseFloat(o1.toString())<(Float.parseFloat(o2.toString()))))this.value=true;
		break;
		
		}
		}
		
		
		
	}

	public Condition(ConditionType t, Condition... c){
		
		
		switch(t){
		case AND:
			
			value=true;
			
			break;
		case OR:
			
			value=false;
			
			break;
		default:
			break;
		
		
		}
		
		for(Condition cc:c){
			switch(t){
			case AND:
				
				if(!cc.get())value=false;
				
				break;
			case OR:
				if(cc.get())value=true;
				break;
			default:
				break;
			
			
			}
			
		
		}
		
		
	}
	public boolean get(){
		return value;
	}
	
	public boolean getNegate(){
		return !value;
	}
	
}
