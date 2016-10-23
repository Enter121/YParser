package me.yaacob.Objects;

import java.util.ArrayList;

public class Table {

	public int length=0;
	public Object object;
	Object[] objects;
	
	public Table(String length){
	this.length=Integer.parseInt(length+"");
	objects=new Object[Integer.parseInt(length+"")];
	}
	public void put(Integer id,Object o){
		objects[Integer.parseInt(id+"")]=o;
	}
	public void set(Integer id){
		object=objects[id];
	}
	
	
}
