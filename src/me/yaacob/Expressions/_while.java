package me.yaacob.Expressions;

import me.yaacob.core.Interpreter;
import me.yaacob.interpreter.Condition;
import me.yaacob.interpreter.Expression;

public class _while extends Expression{

	public _while() {
		super("while");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void make(Interpreter i, String line, Object[] args) {
		
		Condition c=new Condition(i,args[0]);
		String make=line.split("\\)")[1];
		while(c.get()){
			c=new Condition(i,args[0]);
			i.make(make);
		}
		
		
		
		
	}

}
