package com.arong.cookbook.db;

import java.util.ArrayList;


/**
 * @author mrjianrong
 *
 * 功能说明：
 *
 * 创建时间：2017-4-22 上午9:16:23
 */
public class Table {
	public String name;
	public ArrayList<Feild> feilds;

	public Table(String name, ArrayList<Feild> feilds) {
		super();
		this.name = name;
		this.feilds = feilds;
	}

	public Table(String name) {
		super();
		this.name = name;
		this.feilds = new ArrayList<Feild>();
	}

	public void addFeild(Feild feild) {
		this.feilds.add(feild);
	}
	
	public String createSQL(){
		StringBuffer sb=new StringBuffer();
		sb.append("CREATE TABLE "+this.name);
		sb.append("(");
		
		ArrayList<Feild> fs = this.feilds;
		for(Feild f:fs){
			sb.append(f.name);
			sb.append(" ");
			sb.append(f.type);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
}
