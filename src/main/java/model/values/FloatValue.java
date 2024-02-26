package model.values;
import model.types.*;

public class FloatValue implements IValue{
	float value;
	
	public FloatValue(float v){
		this.value = v;
	}
	
	public IType getType() {
		return new FloatType();
	}
	
	public Object getValue() {
		return value;
	}
	
	public boolean equals(Object other) {
		if(other instanceof FloatValue) {
			FloatValue o = (FloatValue)(other);
			return (float)o.getValue() == this.value;
		}
		return false;
	}
	
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public IValue deepcopy() {
		return new FloatValue(value);
	}
}
