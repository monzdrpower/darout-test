package process.core

/** 
 * Interface for key-value pairs container 
 */ 
public interface IProcessData {
	/** 
	 * tests if value for key exists in the container 
	 * 
	 * @param key - key that is being tested 
	 * @return true if key exists in the container,false - otherwise 
	 */ 
	boolean has(String key);
	/** 
	 * obtain value for key 
	 * 
	 * @param key 
	 * @return value 
	 */ 
	String getValue(String key);
}