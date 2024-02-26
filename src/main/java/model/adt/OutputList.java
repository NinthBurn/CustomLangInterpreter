package model.adt;
import java.util.ArrayList;
import java.util.List;
public class OutputList<T> implements IList<T>{
	private ArrayList<T> list;
	
	public OutputList() {
		list = new ArrayList<T>();
	}
	
	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}
	
	public synchronized boolean contains(T new_elem) {
		return list.contains(new_elem);
	}
	
	public synchronized void add(T new_elem) {
		list.add(new_elem);
	}
	
	public synchronized T get(int index) {
		return list.get(index);
	}
	
	public synchronized void remove(T elem) {
		list.remove(elem);
	}
	
	public synchronized void update(int index, T elem) {
		list.set(index, elem);
	}
	
	public synchronized int size() {
		return list.size();
	}

	public synchronized List<T> toList(){
		return this.list;
	}

	public synchronized String toString() {
		String result = "Output:\n";
		for(T elem : list) {
			result += elem.toString() + "\n";
		}
		return result;
	}
}
