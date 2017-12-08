package mum.mpp.carrental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.CountryCodeEnum;
import mum.mpp.carrental.enums.StateEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.factory.BaseFactory;
import mum.mpp.carrental.factory.CarFactory;
import mum.mpp.carrental.factory.CarRentalFactory;
import mum.mpp.carrental.factory.CustomerFactory;
import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.CarRental;
import mum.mpp.carrental.model.Customer;
import mum.mpp.carrental.rulesets.RuleException;

public class DummyDataGenerator {
	static List<Customer> customers = new ArrayList<Customer>();
	static List<Car> cars = new ArrayList<Car>();
	static String street = "4th street";
	static String city = "Fairfield";
	static StateEnum state = StateEnum.IOWA;
	static String zip = "52557";
	static CountryCodeEnum country = CountryCodeEnum.US;
	static String phone = "555 555 555";
	
	public static void main(String[] args) throws RuleException {
		// TODO Auto-generated method stub
		//remove_all_data();
		
		//insert_customer();
		insert_cars();
		insert_rents();
	}
	
	static void remove_all_data() {
		
		Session session = BaseFactory.openSession();
		
		//Query query = session.createQuery( "delete from cars;" );
		
//		User user = (User) session.createCriteria( User.class )
//				.add( Restrictions.eq( "id", id ) )
//				.uniqueResult();
		
		session.close();
		
	}
	
	static void insert_customer() throws RuleException {
	
		Map<String, Integer> hashMap = new HashMap<String, Integer>();
		
		hashMap.put("Bruno Mars", 0);
		hashMap.put("Kanye West", 0);
		hashMap.put("DJ Khaled", 0);
		hashMap.put("Wiz Khalifa", 0);
		hashMap.put("Chris Brown", 0);
		
		for(String s: hashMap.keySet()) {
			Customer c = new Customer(s.split(" ")[0].toLowerCase(), s.toLowerCase().replaceAll("\\s",""),
					s, street, city, state, zip, country, phone);
			
			CustomerFactory.saveCustomer(c);
			customers.add(c);
		}
	}
	
	static void insert_cars() throws RuleException {
		Map<String, Double> hashMap = new HashMap<String, Double>();
		
		hashMap.put("928388", 73.00);
		hashMap.put("388833", 83.00);
		hashMap.put("211322", 76.00);
		hashMap.put("444112", 59.00);
		hashMap.put("441231", 102.00);
		hashMap.put("677744", 90.00);
		
		for(Map.Entry<String, Double> entry : hashMap.entrySet()) {
		    String key = entry.getKey();
		    Double value = entry.getValue();

		    Car c = new Car(CarColorEnum.Black, value, VehicleStatusEnum.Available, key);
			c.setDescription("new data");
			CarFactory.saveCar(c);
			cars.add(c);
		}
	}
	
	static void insert_rents() throws RuleException {
		//CarRental(Customer customer, Car car, LocalDate rentDate, LocalDate returnDate)
		
		// First Customer
		CarRental cr = new CarRental(customers.get(0), cars.get(0) , LocalDate.of(2017, 5, 21), LocalDate.of(2017, 6, 3));
		CarRentalFactory.saveCarRental(cr);
		cr = new CarRental(customers.get(0), cars.get(1) , LocalDate.of(2017, 7, 1), LocalDate.of(2017, 7, 5));
		CarRentalFactory.saveCarRental(cr);
		
		// Second Customer
		cr = new CarRental(customers.get(1), cars.get(0) , LocalDate.of(2017, 8, 15), LocalDate.of(2017, 8, 27));
		CarRentalFactory.saveCarRental(cr);
		
		// Third Customer
		cr = new CarRental(customers.get(2), cars.get(3) , LocalDate.of(2016, 1, 15), LocalDate.of(2016, 2, 1));
		CarRentalFactory.saveCarRental(cr);
		
		// Fourth Customer
		cr = new CarRental(customers.get(3), cars.get(2) , LocalDate.of(2017, 11, 3), LocalDate.of(2017, 11, 7));
		CarRentalFactory.saveCarRental(cr);
		cr = new CarRental(customers.get(3), cars.get(2) , LocalDate.of(2017, 11, 25), LocalDate.of(2017, 12, 10));
		CarRentalFactory.saveCarRental(cr);
		cr = new CarRental(customers.get(3), cars.get(5) , LocalDate.of(2017, 12, 15), LocalDate.of(2017, 1, 10));
		CarRentalFactory.saveCarRental(cr);
		
		// Fifth Customer
		cr = new CarRental(customers.get(4), cars.get(4) , LocalDate.of(2017, 12, 1), LocalDate.of(2017, 12, 6));
		CarRentalFactory.saveCarRental(cr);
	}
}
