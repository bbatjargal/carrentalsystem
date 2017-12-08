package mum.mpp.carrental.factory;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;

import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.rulesets.RuleException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarFactoryTest {


	@Test
	public final void testSearchByValue() {
		try {
			List<Car> cars = CarFactory.search("A902343");
			if(cars.size() > 0)
				assertTrue("searched successfully", true);
			else
				fail("No data found");				
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	public final void testSaveCar() {
		Car car = new Car(CarColorEnum.Black, 2000, VehicleStatusEnum.Available, "A902343");
		car.setDescription("Lx");
		try {
		
			CarFactory.saveCar(car);
			assertTrue("Car created successfully", true); 
		} catch (RuleException e) {
			e.printStackTrace();
			fail(e.getMessage()); 
		}		
	}


	public final void testSelectAll() {
		try {
			List<Car> cars = CarFactory.selectAll();
			if(cars.size() > 0)
				assertTrue("selected all successfully", true);
			else
				fail("No data found");				
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public final void testFindOneLong() {
		try {
			Car car = CarFactory.findOne((long)2);
			if(car != null &&  car.getLicenseNumber() != null 
					&& car.getLicenseNumber().equals("A902343")){
				assertTrue("found one successfully", true);
			}else
				fail("No found");	
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}
	
	public final void testDeleteCar() {
		try {
			Car car = new Car(CarColorEnum.Black, 200, VehicleStatusEnum.Available, "A902343");
			car.setVehicleId((long)3);
			CarFactory.deleteCar(car);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	//@Test
	public final void runAll() {
		testSaveCar();
		testFindOneLong();
		testSelectAll();
		testDeleteCar();
	}
}
