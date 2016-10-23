package me.yaacob.interpreter;

import me.yaacob.core.Interpreter;

public abstract class Expression {

	public String name;
	public Expression(String name){
		this.name=name;
	}
	public abstract void make(Interpreter i, String line ,Object[] args);
	
	
}
