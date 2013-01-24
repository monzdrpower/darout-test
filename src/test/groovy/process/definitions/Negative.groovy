package process.definitions

import process.core.AProcessStep;

class Negative extends AProcessStep {

	@Override
	public void perform() throws Exception {
		set('y', -(getNumber('x1')))
	}

}
