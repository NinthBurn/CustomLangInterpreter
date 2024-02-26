package model.adt;
public class SymbolTable<K, V> extends MyDictionary<K, V>{
	public SymbolTable() {
		super();
	}
		
	public String toString() {
		String result = "Symbol Table:\n" + this.dictString();
		return result;
	}
}
