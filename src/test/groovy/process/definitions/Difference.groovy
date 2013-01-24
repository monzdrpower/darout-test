package process.definitions

import process.core.AProcessStep;

class Difference extends AProcessStep {

	@Override
	public void perform() throws Exception {
		set('y', getNumber('x1') - getNumber('x2'))
	}

}
