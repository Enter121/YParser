package me.yaacob.interpreter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Type {

	public Class class_;
	public HashMap<String, Method> methods = new HashMap<>();
	public HashMap<String, Field> fields = new HashMap<>();
	public String name;
	public Type(Class c){
		this.class_=c;
		name=c.getSimpleName();
		for(Method m:c.getDeclaredMethods()){
			methods.put(m.getName(), m);
		}
		
		for(Field m:c.getDeclaredFields()){
			fields.put(m.getName(), m);
		}
		
	}
	
}
