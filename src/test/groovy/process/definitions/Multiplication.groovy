package process.definitions

import process.core.AProcessStep;

class Multiplication extends AProcessStep {

	@Override
	public void perform() throws Exception {
		set('y', getNumber('x1') * getNumber('x2'))
	}

}
