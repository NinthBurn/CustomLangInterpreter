package model.values;
import model.types.IType;

public interface IValue {
	IType getType();
	Object getValue();
	boolean equals(Object other);
	String toString();
	
	IValue deepcopy();
}
