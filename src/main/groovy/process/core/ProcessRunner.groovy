package process.core

/**
 * <p>
 * java -Dinput.file=process.properties -jar process.jar
 * 
 * <p>
 * process.properties and input.properties - 
 * set full filepath (e.g. <b>d:/dir/process.properties</b>) or set <b>-Dmy.working.directory</b> system property
 * <p>
 * @author denisov
 *
 */
class ProcessRunner {

	static String MY_WORKING_DIR = 'my.working.dir'
	
	public static void main(String[] args) {

		try {

			def processProperties = getProcessProperties()

			ProcessData context = createContext(processProperties)

			stepProcessing(processProperties, context)

			generateOutput(processProperties, context)
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static ProcessData createContext(Properties processProperties) {
		def inputProperties = getInputProperties(processProperties)
		ProcessData context = ProcessData.getInstance()
		context.init(inputProperties)
		return context
	}

	private static generateOutput(Properties processProperties, ProcessData context) {
		def outputFileName = processProperties['process.output'].trim()
		def outputKeys = processProperties['output.keys'].trim()

		File outputFile = new File(outputFileName)

		// Groovy syntactic sugar: split string to array, iterate over it,
		// collect new list with items {"result1=3.0", "result2=0.5"} and join it to the string with '\n'
		def result = outputKeys.split(',').collect { k -> "${k}=${context.getValue(k)}" }.join('\n')

		outputFile.text = result
	}

	private static stepProcessing(Properties processProperties, ProcessData context) {
		int stepNumber = 0;

		while(true){

			stepNumber++;

			def stepClassName = processProperties["step${stepNumber}.class"]?.trim()

			if(!stepClassName)
				break

			AProcessStep step = loadClass(stepClassName).newInstance()
			step.setInput(context)

			def stepInput = processProperties["step${stepNumber}.input"].trim()
			step.parseInputData(stepInput)

			step.perform()

			def stepOutput = processProperties["step${stepNumber}.output"].trim()
			step.parseOutputData(stepOutput)

		}
	}

	private static Properties getInputProperties(Properties processProperties) {
		def processInput = processProperties['process.input'].trim()
		File inputPropertiesFile = new File(processInput)
		if(!inputPropertiesFile.exists()) {
			def workingDir = System.properties.get(MY_WORKING_DIR)
			inputPropertiesFile = new File(workingDir + System.properties['file.separator'] + processInput)
		}

		def inputProperties = new Properties()
		inputProperties.load(new FileReader(inputPropertiesFile))
		return inputProperties
	}

	private static Properties getProcessProperties() {
		def processProperties = new Properties()
		def inputFile = System.properties.get('input.file')
		def workingDir = System.properties.get(MY_WORKING_DIR)
		if(!workingDir){
			workingDir = new File("").getCanonicalPath()
			System.properties[MY_WORKING_DIR] = workingDir
		}
		File processPropertiesFile = new File(workingDir + System.properties['file.separator']+ inputFile)
		processProperties.load(new FileReader(processPropertiesFile))
		return processProperties
	}

	/**
	 * step classes from classpath or my.working.dir
	 * @param stepClassName
	 * @return
	 */
	private static Class loadClass(String stepClassName) {
		Class cl
		try {
			cl = Class.forName(stepClassName)
		} catch (ClassNotFoundException e){
			URL url = (new File(System.properties[MY_WORKING_DIR]).toURI()).toURL()
			ClassLoader loader = new URLClassLoader([url] as URL[])
			cl = loader.loadClass(stepClassName);
		}
		return cl
	}
}
