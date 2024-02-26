package model.types;
import model.values.IValue;

public interface IType {
	String toString();
	boolean equals(Object o);
	IValue defaultValue();
	
	IType deepcopy();
}
