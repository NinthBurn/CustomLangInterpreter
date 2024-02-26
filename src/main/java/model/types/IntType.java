package model.types;
import model.values.*;

public class IntType implements IType{
	public boolean equals(Object other) {
		if(other instanceof IntType)
			return true;
		return false;
	}
	
	public IValue defaultValue() {
		return new IntValue(0);
	}
	
	public String toString() {
		return "int";
	}
	
	public IType deepcopy() {
		return new IntType();
	}
}
