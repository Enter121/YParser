package me.yaacob.Expressions;

import me.yaacob.core.Interpreter;
import me.yaacob.interpreter.Condition;
import me.yaacob.interpreter.Expression;
import me.yaacob.interpreter.Type;
import me.yaacob.interpreter.Variable;

public class _for extends Expression{

	public _for() {
		super("for");
	
	}

	@Override
	public void make(Interpreter i, String line, Object[] args) {
		
	
		if(args.length!=3)
			try {
				throw new Exception("for must have 3 args splitted with ';' ");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	
		
		i.make(args[0].toString());
		
		Condition c = new Condition(i,args[1]);
		String make=line.split("\\)")[1];
		for(;c.get();i.make(args[2].toString())){
			c=new Condition(i,args[1]);
			i.make(make);
	
		}
		String str=args[0].toString();
		for(Type t:i.types){
			
			
			str=str.replace(t.name, "");
		}
		
		i.removeVar(str.split("=")[0]);
		
		
	}

}
