package me.yaacob.interpreter;

public abstract class Expression {

	public String name;
	public Expression(String name){
		this.name=name;
	}
	public abstract void make(Object[] args);
	
	
}
