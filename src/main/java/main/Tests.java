package main;

import model.values.*;
import model.types.*;

public class Tests {
	public void testValues() {
		// create value variables of different types and values
		IValue v1 = new IntValue(5);
		IValue v2 = new IntValue(6);
		IValue v3 = new IntValue(5);
		IValue v4 = new BoolValue(true);
		
		//check if they have been assigned properly
		assert(v1.getValue().equals(5));
		assert(v2.getValue().equals(6));
		assert(v3.getValue().equals(5));
		assert(v4.getValue().equals(true));
		
		// check if they return their type properly
		assert(v1.getType().equals(new IntType()));
		assert(v2.getType().equals(new IntType()));
		assert(v4.getType().equals(new BoolType()));

		assert(v1.getType().equals(v2.getType())); // same type
		assert(v1.getType().equals(v3.getType())); // same type
		assert(!v1.getType().equals(v4.getType())); // different type
		
		// check if they return their value properly
		assert(!v1.getValue().equals(v2.getValue()));
		assert(v1.getValue().equals(v3.getValue()));
		assert(!v1.getValue().equals(v4.getValue()));
		
		//System.out.println(v2.getType().toString() + " " + v2.toString() + " " + v4.getType().toString() + " " + v4.toString());
		System.out.println("Values & types test is successful");
	}

	public void testExpressions() {
		
	}
}
