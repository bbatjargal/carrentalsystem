package mum.mpp.carrental.rulesets;

import java.util.regex.Pattern;

import mum.mpp.carrental.model.Car;

/**
 * Rules:
 * 1. All fields must be nonempty.
 * 2. Base rent cost must be greater than 0.0
 *
 */
public class CarRuleSet implements RuleSet {
	private Car car;
	
	public void applyRules(Object ob) throws RuleException {
		this.car = (Car)ob;
		this.nonEmptyRule();
		this.baseRentCostRule();
	}

	private void nonEmptyRule() throws RuleException {
		if(this.car.getLicenseNumber() == null || this.car.getLicenseNumber().trim().isEmpty()
				|| this.car.getDescription() == null || this.car.getDescription().trim().isEmpty()
				|| this.car.getColor() == null) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

	
		
	private void baseRentCostRule() throws RuleException {

		if(this.car.getBaseRentCost() <= 0) {
			throw new RuleException("Base rent cost must be greater than zero!");
		}
	}
}