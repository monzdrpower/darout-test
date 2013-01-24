package process.core

/** process data context, singleton
 * 
 * @author denisov
 *
 */
class ProcessData implements IProcessData {

	private Map data = [:]
	
	private static ProcessData processData = null;
	
	private ProcessData(){
	}
	
	static ProcessData getInstance(){
		if(!processData)
			processData = new ProcessData()
		processData
	}
	
	void init(Properties inputProperties){
		inputProperties.each { k, v ->
			data[k] = v.trim()
		}
	}
	
	@Override
	boolean has(String key) {
		return (data[key] != null)
	}

	@Override
	String getValue(String key) {
		return data[key]
	}

	void add(String key, Object value) {
		data[key] = value
	}

}
