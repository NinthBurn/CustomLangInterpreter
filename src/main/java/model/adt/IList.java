package model.adt;
import java.util.List;

public interface IList<T>{
	void add(T new_elem);
	void remove(T new_elem);
	void update(int position, T new_elem);
	T get(int index);
	boolean isEmpty();
	boolean contains(T elem);
	int size();
	List<T> toList();
	
	String toString();
}
