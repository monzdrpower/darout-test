package process.core

/**
 * Abstract class for process step - implements common methods used in every process step implementation
 *
 */
public abstract class AProcessStep implements IProcessStep {
	
	private Map stepData = [:] // groovy short syntax for new HashMap()

	private ProcessData processData
	
	/** 
	 * @param input e.g. "a:x1,b:x2" means "stepData.x1 = processData.a; stepData.x2 = processData.b" 
	 * 
	 */
	void parseInputData(String input){
		stepData.clear()
		def params = input.split(',')
		for(p in params) {
			def pp = p.split(':')
			stepData[(pp[1])] = processData.getValue(pp[0])
		}
	}
	
	/**
	 * invoked by process runner - transfer input parameters to step
	 *
	 * @param inputData - container of key-value pairs according to process configuration file
	 */
	@Override
	public void setInput(IProcessData inputData) {
		this.processData = inputData 
	}
	
	void parseOutputData(String output){
		def pp = output.split(':')
		processData.add(pp[1], stepData[(pp[0])])
	}

	/**
	 * invoked by process runner - transfer output parameters back to process
	 *
	 }
	 * @return container of key-value pairs according to process configuration file
	 */
	@Override
	public IProcessData getOutput() {
		return processData // stub, not used
	}
	
	@Override
	public abstract void perform() throws Exception;
	
	// common setter
	protected void set(String key, Object value){
		stepData[key] = value
	}

	// typed getters 
	protected Number getNumber(String key){
		def d = stepData[key]
		return new BigDecimal(d)
	}

	protected String getString(String key){
		return stepData[key]
	}

	protected Boolean getBoolean(String key){
		def value = stepData[key]
		return ('true'.equalsIgnoreCase(value))
	}

}