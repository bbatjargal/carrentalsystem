package mum.mpp.carrental.factory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.rulesets.RuleException;

final public class CarFactory extends BaseFactory {
	
	public static Car saveCar(Car car) throws RuleException {
		if(car == null)
			throw new NullPointerException("Car can not be null!");
		return save(car);
	}
	
	public static void deleteCar(Car car) throws Exception {
		delete(car);
	}

	public static List<Car> selectAll() throws SQLException{
		return selectAll(Car.class);
	}
	
	public static Car findOne(Long id) throws SQLException {
		if(id == null)
			throw new NullPointerException("Id can not be null!");
		return findOne(Car.class, id);
	}
	
	public static List<Car> search(String value) throws SQLException {
		if(value == null)
			throw new NullPointerException("value can not be null!");	

		Criterion crLicenseNumber = Restrictions.ilike("licenseNumber","%" + value +"%");
		Criterion crDescription = Restrictions.ilike("description","%" + value +"%");
//		/Criterion crColor = Restrictions.ilike("color","%" + value +"%");
		//Criterion crStatus = Restrictions.ilike("description","%" + value +"%");
				
		LogicalExpression orExp = Restrictions.or(crLicenseNumber, crDescription);
		

		return search(Car.class, Arrays.asList(orExp));
	}
}
