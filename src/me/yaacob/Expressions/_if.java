package me.yaacob.Expressions;

import me.yaacob.core.Interpreter;
import me.yaacob.interpreter.Condition;
import me.yaacob.interpreter.Expression;

public class _if extends Expression{

	public _if() {
		super("if");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void make(Interpreter i,String line, Object[] args) {
			Condition c=new Condition(i,args[0]);
			i.lastCondition=c;
			String cmd=line.split("\\)")[1];
			
			if(c.get())i.make(cmd);
			
		
	}

}
