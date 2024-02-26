package model.types;
import model.values.*;

public class ReferenceType implements IType{
	private IType referenced_type;
	
	public ReferenceType(IType type){
		referenced_type = type;
	}
	
	public IType getReferenceType(){
		return referenced_type;
	}
	
	public IValue defaultValue(){
		return new ReferenceValue(0, referenced_type);
	}
	
	public boolean equals(Object other){
		if(other instanceof ReferenceType)
		{
			ReferenceType o = (ReferenceType)other;
			return referenced_type.equals(o.getReferenceType());
		}
		return false;
	}
	
	public String toString(){
		return "Ref(" + referenced_type.toString() + ")";
	}
	
	public IType deepcopy() {
		return new ReferenceType(referenced_type.deepcopy());
	}
}