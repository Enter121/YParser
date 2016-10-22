package me.yaacob.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
	
	 
	 
	 
	public static String readFile(File f , boolean app){
		String content="";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.err.println(prefix+"FILE NOT FOUND");
		}
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		    while (line != null) {
		        
		    	sb.append(line);
		    	if(app)sb.append(System.getProperty("line.separator"));
		    	try {
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    content = sb.toString();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return content;
	}
	
	public static String readFile(File f){
		String content="";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.err.println(prefix+"FILE NOT FOUND");
		}
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		    while (line != null) {
		        
		    	sb.append(line);
		    sb.append(System.getProperty("line.separator"));
		    	try {
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    content = sb.toString();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return content;
	}
	
	
	public static void writeFile(File f , String content){
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter(f);
			writer.print(content);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static final String version="1.1.0";
	public static final String build="#1";
	public static final String author="Yaacob";
	public static final String prefix="[YParser] ";
	public static final String title="YParser";
	
	public static String getVersion(){
		return new String("Version: "+version+" Build: "+build);
	}
	public static String getAuthor(){
		return new String(author);
	}
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?"); 
	}
}
