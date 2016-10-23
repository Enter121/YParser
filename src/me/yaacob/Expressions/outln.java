package me.yaacob.Expressions;

import me.yaacob.core.Interpreter;
import me.yaacob.interpreter.Expression;

public class outln extends Expression{

	public outln() {
		super("outln");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void make(Interpreter i,String line,Object[] args) {
		if(args==null){
			System.out.printf(System.getProperty("line.separator"));
		}else
		if(args[0]!=null)System.out.printf(args[0].toString().replace("\"","")+System.getProperty("line.separator"));
	}

}
