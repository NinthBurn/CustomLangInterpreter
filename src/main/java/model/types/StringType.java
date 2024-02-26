package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
	public boolean equals(Object other) {
		if(other instanceof StringType)
			return true;
		return false;
	}
	
	public IValue defaultValue() {
		return new StringValue("");
	}
	
	
	public String toString() {
		return "string";
	}
	
	public IType deepcopy() {
		return new StringType();
	}
}
