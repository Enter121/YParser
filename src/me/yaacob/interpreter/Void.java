package me.yaacob.interpreter;

import me.yaacob.core.Interpreter;

public class Void {
	public String name;
	public Cell c;
	public Void(String name, Cell c) {
		this.c=c;
		this.name=name;
	}
	public void make(Interpreter i){
		c.make(i);
	}
	public Cell getCell(){
		return c;
	}
}
