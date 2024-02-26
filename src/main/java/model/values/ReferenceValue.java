package model.values;
import model.types.*;

public class ReferenceValue implements IValue{
	private int heap_address;
	private IType referenced_type;
	
	public ReferenceValue(int heap_adr, IType type){
		heap_address = heap_adr;
		referenced_type = type;
	}
	
	public int getAddress(){
		return heap_address;
	}
	
	public IType getType(){
		return new ReferenceType(referenced_type);
	}
	
	public IType getReferencedType() {
		return referenced_type;
	}
	
	// this should not be used
	public Object getValue(){
		return this;
	}
	
	public boolean equals(Object other) {
		if(other instanceof ReferenceValue) {
			ReferenceValue o = (ReferenceValue)other;
			return o.getAddress() == heap_address && o.getReferencedType() == referenced_type;
		}
		
		return false;
	}
	
	public String toString(){
		return "(adr: " + heap_address + ", ref_type: " + referenced_type.toString() + ")";
	}
	
	public IValue deepcopy(){
		return new ReferenceValue(heap_address, referenced_type.deepcopy());
	}
}

