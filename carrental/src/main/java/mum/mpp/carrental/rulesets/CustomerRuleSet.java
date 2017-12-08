package mum.mpp.carrental.rulesets;

import java.sql.SQLException;

import mum.mpp.carrental.factory.CustomerFactory;
import mum.mpp.carrental.model.Customer;

/**
 * Rules:
 * 1. All fields must be nonempty.
 * 2. User Name must be unique.
 *
 */
public class CustomerRuleSet implements RuleSet {
	private Customer customer;
	
	public void applyRules(Object ob) throws RuleException {
		this.customer = (Customer)ob;
		nonEmptyRule();
		uniqueUserName();
	}

	private void nonEmptyRule() throws RuleException {
        if(this.customer.getUserName() == null || this.customer.getUserName().trim().isEmpty()
                || this.customer.getPassword() == null || this.customer.getPassword().trim().isEmpty()
                || this.customer.getFullName() == null || this.customer.getFullName().trim().isEmpty()
                || this.customer.getStreet() == null || this.customer.getStreet().trim().isEmpty()
                || this.customer.getCity() == null || this.customer.getCity().trim().isEmpty()
                || this.customer.getState() == null
                || this.customer.getZip() == null || this.customer.getZip().isEmpty()
                || this.customer.getCountry() == null
                || this.customer.getPhoneNumber() == null || this.customer.getPhoneNumber().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}
	
	private void uniqueUserName() throws RuleException{
		try {
			if(this.customer.getUserId() == null
					&& CustomerFactory.isUserNameExist(this.customer.getUserName())) {
				throw new RuleException("User Name must be unique!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
