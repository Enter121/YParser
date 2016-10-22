package me.yaacob.Expressions;

import me.yaacob.interpreter.Expression;

public class out extends Expression{

	public out() {
		super("out");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void make(Object[] args) {
		if(args[0]!=null)System.out.printf(args[0].toString().replace("\\n", System.getProperty("line.separator")).replace("\"", ""));
		
	}

}
