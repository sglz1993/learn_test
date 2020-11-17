package org.py.test.proguard02.entity;

public class User {
	private String name="王五";
	private int age=15;
	private String sex="男";
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	private String information(){		
		return "姓名："+name+"年龄："+age+"性别:"+sex;
	}
}
