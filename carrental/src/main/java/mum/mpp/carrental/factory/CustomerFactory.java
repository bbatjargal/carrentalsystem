package mum.mpp.carrental.factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import mum.mpp.carrental.model.Customer;
import mum.mpp.carrental.rulesets.RuleException;

public final class CustomerFactory extends BaseFactory {

	public static Customer saveCustomer(Customer cust) throws RuleException {
		if(cust == null)
			throw new NullPointerException("Customer can not be null!");
		return save(cust);
	}
	
	public static void deleteCustomer(Customer cust) throws Exception {
		delete(cust);
	}

	public static List<Customer> selectAll() throws SQLException{
		return selectAll(Customer.class);
	}
	
	public static Customer findOne(Long id) throws SQLException {
		if(id == null)
			throw new NullPointerException("Id can not be null!");
		return findOne(Customer.class, id);
	}
	
	public static List<Customer> search(String value) throws SQLException {
		if(value == null)
			throw new NullPointerException("value can not be null!");	

		Criterion cri0 = Restrictions.ilike("userName","%" + value +"%");
		Criterion cri1 = Restrictions.ilike("fullName","%" + value +"%");
		Criterion cri2 = Restrictions.ilike("phoneNumber","%" + value +"%");

		LogicalExpression orExp1 = Restrictions.or(cri0, cri1);
		LogicalExpression orExp2 = Restrictions.or(orExp1, cri2);
		

		return search(Customer.class, Arrays.asList(orExp2));
	}
	
	public static boolean isUserNameExist(String userName) throws SQLException {
		Criterion cri = Restrictions.eq("userName", userName);
		
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		criterions.add(cri);
		
		List<Customer> customers = searchByCriterions(Customer.class, criterions);
		
		return customers != null && !customers.isEmpty();
	}
}
