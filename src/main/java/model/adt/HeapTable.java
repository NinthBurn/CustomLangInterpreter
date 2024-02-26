package model.adt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;

public class HeapTable<K, V> implements IDictionary<K, V>{
	protected ConcurrentHashMap<K, V> hash_map;
	private Integer next_free_address;
	
	public HeapTable() {
		hash_map = new ConcurrentHashMap<K, V>();
		next_free_address = 1;
	}
	
	public synchronized boolean isEmpty() {
		return hash_map.isEmpty();
	}
	
	public synchronized boolean isDefined(K key) {
		return hash_map.containsKey(key);
	}
	
	public synchronized V get(K key) throws ADTException{
		if(!hash_map.containsKey(key))
			throw new ADTException("Key is not defined");
			
		return hash_map.get(key);
	}
	
	public synchronized Integer get_free_address() {
		while(hash_map.containsKey(next_free_address))
			next_free_address += 1;
		
		return next_free_address;
	}
	
	public synchronized void put(K key, V value) {
		hash_map.put(key, value);
	}
	
	public synchronized void remove(K key) {
		hash_map.remove(key);
	}
	
	public synchronized void update(K key, V value) {
		hash_map.replace(key, value);
	}
	
	public synchronized int size() {
		return hash_map.size();
	}
	
	public synchronized Set<Map.Entry<K, V>> entrySet(){
		return hash_map.entrySet();
	}
	
	public synchronized void setContent(Map<K, V> map) {
		hash_map = new ConcurrentHashMap<K,V>(map);
	}
	
	public synchronized Map<K, V> getContent(){
		return hash_map;
	}
	
	public String dictString() {
		String result = "";
		for (Map.Entry<K, V> set : hash_map.entrySet()) {
           result += set.getKey() + " -> " + set.getValue() + "\n";
		}
		return result;
	}
		
	public String toString() {
		String result = "Heap table:\n";
		for (Map.Entry<K, V> set : hash_map.entrySet()) {
           result += set.getKey() + " -> " + set.getValue() + "\n";
		}
		return result;
	}
}

