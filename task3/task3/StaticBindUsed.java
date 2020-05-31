package task3;

/**
 * Static Binding is resolved at compile time by compiler. 
 * It depends on type of object and not dependent on object instance
 * 
 * @author Lee Yoon Jeong
 *
 */
public class StaticBindUsed {
	
	
	public static class A {
		static void print() {
			System.out.println("From superclass.");
			}
	}

	public static class B extends A {
		static void print() {
			System.out.println("From subclass.");
		}
	}

	public static void main(String[] args) {
		A a = new A();
		A b = new B();
		a.print();
		b.print();
	}
	
	/*
	 * Output: 
	 * 
	 * 	From superclass.
	 * 	From superclass.
	 */
	
	/*
	 * Above program prints same output two times. 
	 * it is static binding. since the methods are 
	 * static and type of variables in main methods 
	 * is A (i.e super type ) so both times print method in super class only called.
	 */
		
}
