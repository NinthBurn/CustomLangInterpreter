package model.adt;
import java.util.List;
public interface IStack<T>{
	boolean isEmpty();
	T pop() throws ADTException;
	void push(T new_T);
	int size();
	List<T> toList();
	String toString();
}
