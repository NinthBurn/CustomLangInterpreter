package model.types;

import model.values.IValue;
import model.values.FloatValue;

public class FloatType implements IType{
	public boolean equals(Object other) {
		if(other instanceof FloatType)
			return true;
		return false;
	}
	
	public IValue defaultValue() {
		return new FloatValue((float)0.0);
	}
	
	
	public String toString() {
		return "float";
	}
	
	public IType deepcopy() {
		return new FloatType();
	}
}
