package model.types;

import model.values.IValue;
import model.values.BoolValue;

public class BoolType implements IType{
	public boolean equals(Object other) {
		if(other instanceof BoolType)
			return true;
		return false;
	}
	
	public IValue defaultValue() {
		return new BoolValue(false);
	}
	
	public String toString() {
		return "bool";
	}
	
	public IType deepcopy() {
		return new BoolType();
	}
}
