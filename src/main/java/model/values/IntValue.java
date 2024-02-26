package model.values;
import model.types.*;

public class IntValue implements IValue{
	int value;
	
	public IntValue(int v){
		this.value = v;
	}
	
	public IType getType() {
		return new IntType();
	}
	
	public Object getValue() {
		return value;
	}
	
	public boolean equals(Object other) {
		if(other instanceof IntValue) {
			IntValue o = (IntValue)(other);
			return (int)o.getValue() == this.value;
		}
		return false;
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public IValue deepcopy() {
		return new IntValue(value);
	}
}
