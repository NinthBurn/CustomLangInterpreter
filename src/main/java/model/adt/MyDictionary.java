package model.adt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;
public class MyDictionary<K, V> implements IDictionary<K, V>{
	protected ConcurrentHashMap<K, V> hash_map;
	
	public MyDictionary() {
		hash_map = new ConcurrentHashMap<K, V>();
	}
	
	public boolean isEmpty() {
		return hash_map.isEmpty();
	}
	
	public boolean isDefined(K key) {
		return hash_map.containsKey(key);
	}
	
	public V get(K key) throws ADTException{
		if(!hash_map.containsKey(key))
			throw new ADTException("Key is not defined");
			
		return hash_map.get(key);
	}
	
	public void put(K key, V value) {
		hash_map.put(key, value);
	}
	
	public void remove(K key) {
		hash_map.remove(key);
	}
	
	public void update(K key, V value) {
		hash_map.replace(key, value);
	}
	
	public int size() {
		return hash_map.size();
	}
	
	public Set<Map.Entry<K, V>> entrySet(){
		return hash_map.entrySet();
	}
	
	public void setContent(Map<K, V> map) {
		hash_map = new ConcurrentHashMap<K,V>(map);
	}
	
	public Map<K, V> getContent(){
		return hash_map;
	}
	
	public String dictString() {
		String result = "";
		for (Map.Entry<K, V> set : hash_map.entrySet()) {
           result += set.getKey() + " -> " + set.getValue() + "\n";
		}
		return result;
	}
}
