package process.definitions

import process.core.AProcessStep;

class SquareRoot extends AProcessStep {

	@Override
	public void perform() throws Exception {
		set('y', Math.sqrt(getNumber('x1')))
	}

}
