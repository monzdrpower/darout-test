package process.core

/**
 * Interface for process step
 */
public interface IProcessStep {
	/**
	 * implements logic of particular step
	 *
	 * @throws Exception
	 */
	void perform() throws Exception;
	/**
	 * invoked by process runner - transfer input parameters to step
	 *
	 * @param inputData - container of key-value pairs according to process configuration file
	 */
	void setInput(IProcessData inputData);
	/**
	 * invoked by process runner - transfer output parameters back to process
	 *
	 * @return container of key-value pairs according to process configuration file
	 */
	IProcessData getOutput();
}