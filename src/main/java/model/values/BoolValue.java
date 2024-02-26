package model.values;
import model.types.*;

public class BoolValue implements IValue{
	boolean value;
	
	public BoolValue(boolean v){
		this.value = v;
	}
	
	public IType getType() {
		return new BoolType();
	}
	
	public Object getValue() {
		return value;
	}
	
	public boolean equals(Object other) {
		if(other instanceof BoolValue) {
			BoolValue o = (BoolValue)other;
			return (boolean)o.getValue() == this.value;
		}
		return false;
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public IValue deepcopy() {
		return new BoolValue(value);
	}
}
