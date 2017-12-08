package mum.mpp.carrental.rulesets;

import mum.mpp.carrental.model.CarRental;

/**
 * Rules:
 * 1. All fields must be nonempty.
 * 2. Base rent cost must be greater than 0.0
 *
 */
public class CarRentalRuleSet implements RuleSet {
	private CarRental carRental;
	
	public void applyRules(Object ob) throws RuleException {
		this.carRental = (CarRental)ob;
		nonEmptyRule();
	}

	private void nonEmptyRule() throws RuleException {
		if(this.carRental.getRentDate() == null || this.carRental.getRentDate() == null) {
			throw new RuleException("All fields must be non-empty!");
		}
	}
}