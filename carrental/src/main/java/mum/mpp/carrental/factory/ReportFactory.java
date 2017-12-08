package mum.mpp.carrental.factory;

import java.sql.SQLException;
import java.time.LocalDate;
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

public final class ReportFactory extends BaseFactory {
	
	public static List<CarRental> search(LocalDate beginDate, LocalDate endDate) throws SQLException{
		
		Criterion cri = Restrictions.between("rentDate", beginDate, endDate);
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		criterions.add(cri);
		
		return searchByCriterions(CarRental.class, criterions);
	}
	
}
