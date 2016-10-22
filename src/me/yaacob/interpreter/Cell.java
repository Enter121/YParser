package me.yaacob.interpreter;

import java.util.ArrayList;

import me.yaacob.core.Interpreter;

public class Cell {

	ArrayList<String> lines =new ArrayList<>();
	public String content , id;
	public int begin,end;
	public Cell setBegin(int b){
		this.begin=b;
		return this;
	}
	public Cell setEnd(int e){
		this.end=e;
		return this;
	}
	
	public void generate(String content){
	this.content=content.substring(begin, end);
	}
	public void split(){
		String[] s=content.replace("{", "").replaceAll("}", "").split(";");
		for(String line: s){
			lines.add(line);
		}
	}
	
	public Cell(String id){
		this.id=id;
	}
	public void make(Interpreter i){
		for(String line: lines){
			i.make(line);
		}
	}
}
