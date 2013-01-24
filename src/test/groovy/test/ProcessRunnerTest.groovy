package test

import org.junit.Test

import process.core.ProcessRunner

class ProcessRunnerTest {

	@Test
	void test(){
		
		def workingDir = 'src/test/resources'
		System.setProperty("input.file", "process.properties")
		System.setProperty(ProcessRunner.MY_WORKING_DIR, workingDir)
		new ProcessRunner().main()
		def file = new File(workingDir+'/output.txt')
		file.delete()
	}
}
