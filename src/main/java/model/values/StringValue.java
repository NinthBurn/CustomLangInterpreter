package model.values;
import model.types.*;

public class StringValue implements IValue{
	String value;
	
	public StringValue(String t){
		this.value = t;
	}
	
	public IType getType() {
		return new StringType();
	}
	
	public Object getValue() {
		return value;
	}
	
	public boolean equals(Object other) {
		if(other instanceof StringValue) {
			StringValue o = (StringValue)(other);
			return ((String)o.getValue()).equals(this.value);
		}
		return false;
	}
	
	public String toString() {
		return "\"" + this.value + "\"";
	}
	
	public IValue deepcopy() {
		return new StringValue(value);
	}
}
