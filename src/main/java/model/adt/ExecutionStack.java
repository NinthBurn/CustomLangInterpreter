package model.adt;
import java.util.ArrayDeque;
import java.util.List;
public class ExecutionStack<T> implements IStack<T>{
	private ArrayDeque<T> stack;
	
	public ExecutionStack(){
		stack = new ArrayDeque<T>();
	}
	
	public boolean isEmpty(){
		return stack.isEmpty();
	}
	
	public T pop() throws ADTException{
		if(stack.size() == 0)
			throw new ADTException("Stack is empty");
		return stack.pop();
	}

	public T top(){
		if(stack.size() == 0)
			throw new RuntimeException("Stack is empty");
		return stack.getFirst();
	}

	public void push(T new_elem) {
		stack.push(new_elem);
	}
	
	public int size() {
		return stack.size();
	}

	public synchronized List<T> toList(){
		return this.stack.stream().toList();
	}

	public String toString() {
		String result = "Execution Stack content:\n";
		for(T elem : stack) {
			result += elem.toString() + "\n";
		}
		return result;
	}
}
