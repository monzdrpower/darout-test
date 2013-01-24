package process.definitions

import process.core.AProcessStep;

class Sum extends AProcessStep {

	@Override
	public void perform() throws Exception {
		set('y', getNumber('x1') + getNumber('x2'))
	}

}
