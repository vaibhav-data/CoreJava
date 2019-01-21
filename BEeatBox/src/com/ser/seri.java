package com.ser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class seri implements Serializable{

	String name;
	int age;
	
	public seri(String name,int age){
		this.name = name;
		this.age = age;
	}
	
	public void saveState(seri a) throws FileNotFoundException{
		FileOutputStream fs = new FileOutputStream("test.ser");
		try {
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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

	public seri readState() throws FileNotFoundException {
		try {
			FileInputStream fs = new FileInputStream("test.ser");
			ObjectInputStream os = new ObjectInputStream(fs);
			try {
				seri one = (seri) os.readObject();
				System.out.println(one.getAge() + "" + one.getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}
}
