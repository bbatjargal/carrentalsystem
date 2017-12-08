package mum.mpp.carrental.factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.CarRental;
import mum.mpp.carrental.rulesets.RuleException;

public final class CarRentalFactory extends BaseFactory {
	public static CarRental saveCarRental(CarRental cr) throws RuleException {
		if(cr == null)
			throw new NullPointerException("Car Rental can not be null!");
		return save(cr);
	}
	
	public static void deleteCarRental(CarRental cr) throws Exception {
		delete(cr);
	}

	public static List<CarRental> selectAll() throws SQLException{
		return selectAll(CarRental.class);
	}
	
	public static CarRental findOne(Long id) throws SQLException {
		if(id == null)
			throw new NullPointerException("Id can not be null!");
		return findOne(CarRental.class, id);
	}
	
	public static List<CarRental> filterByCustomer(Long userId) throws SQLException {
		Criterion cri = Restrictions.eq("customer.userId", userId);
		
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		criterions.add(cri);
		
		return searchByCriterions(CarRental.class, criterions);
	}
	
	public static List<CarRental> filterByCar(Long vehicleId) throws SQLException {
		Criterion cri = Restrictions.eq("car.vehicleId", vehicleId);
		
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		criterions.add(cri);
		
		return searchByCriterions(CarRental.class, criterions);
	}

	public static List<Object> saveAll(List<Object> objs) throws Exception {
		return BaseFactory.saveAll(objs);
	}
}
