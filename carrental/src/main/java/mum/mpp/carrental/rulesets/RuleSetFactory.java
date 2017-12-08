package mum.mpp.carrental.rulesets;

import java.util.HashMap;

import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.CarRental;
import mum.mpp.carrental.model.Customer;

final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<?>, RuleSet> map = new HashMap<Class<?>, RuleSet>();
	
	static {
		map.put(CarRental.class, new CarRentalRuleSet());
		map.put(Customer.class, new CustomerRuleSet());
		map.put(Car.class, new CarRuleSet());
	}
	
    public static boolean validateRuleSet(Object c) throws RuleException {
        Object cl = c.getClass();

        if(!map.containsKey(cl)) {
            throw new IllegalArgumentException("No RuleSet found for this Component");
        }
        
        RuleSet rules = map.get(cl);
        
        rules.applyRules(c);
        
        return true;
	}
}
