package me.yaacob.interpreter;

public class Variable {
	public Type t;
	public Object value;
	public String name;
	public Variable(Type t, String name, Object value){
		this.t=t;
		this.name=name;
		this.value=value;
	}
}
