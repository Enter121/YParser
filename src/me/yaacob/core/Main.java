/**
 * 
 */
package me.yaacob.core;

import java.io.File;

/**
 * @author yaacob
 *
 */
public class Main {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Interpreter i=new Interpreter();
		String str=Utils.readFile(new File("code"),false);
		i.parse(str);
		
		
		
	}

}
