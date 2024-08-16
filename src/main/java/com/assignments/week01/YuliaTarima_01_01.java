package com.assignments.week01;

public class YuliaTarima_01_01 {

	public static void main(String[] args) {
		
		// This calls the other modules and the answer should display as 35. 	
		int answer = division(multiplication(addition(5,2),10),2); 
		System.out.printf("The answer to ((5+2)x10)/2 is %d.", answer); 

	}
	
	public static int addition(int a,int b) {
		//adds 2 integers together
		return a + b; 		
	}
	
	public static int multiplication(int a,int b) {
		//multiplies 2 integers together
		return a * b; 		
	}

	public static int division(int a,int b) {
		//divides 2 integers together
		return a / b;
	}

}