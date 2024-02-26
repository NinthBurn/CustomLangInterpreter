package model.adt;
import java.util.Set;
import java.util.Map;
public interface IDictionary<A, B> {
	boolean isEmpty();
	boolean isDefined(A key);
	void put(A key, B value);
	void remove(A key);
	void update(A key, B value);
	B get(A key) throws ADTException;
	int size();
	Set<Map.Entry<A, B>> entrySet();
	void setContent(Map<A, B> map);
	Map<A, B> getContent();
	String toString();
}
