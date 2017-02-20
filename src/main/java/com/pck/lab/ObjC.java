package com.pck.lab;

/*
 * Latest recommendations to use Enum to represent Singletons in Java:
 * http://javarevisited.blogspot.hk/2012/07/why-enum-singleton-are-better-in-java.html
 * http://docs.oracle.com/javase/1.5.0/docs/guide/language/enums.html
 * http://www.drdobbs.com/jvm/creating-and-destroying-java-objects-par/208403883?pgno=3
 */
public enum ObjC {
	INSTANCE;

	private static volatile int counter = 0;

	public static ObjC getInstance() {
		return INSTANCE;
	}

	public void increment() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}
}
