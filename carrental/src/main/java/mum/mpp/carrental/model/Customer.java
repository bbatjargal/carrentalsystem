package mum.mpp.carrental.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import mum.mpp.carrental.enums.CountryCodeEnum;
import mum.mpp.carrental.enums.StateEnum;
import mum.mpp.carrental.enums.UserRoleEnum;

@Entity(name = "Customers")
public class Customer extends User {
	// DB Fields
	private String fullName;
	private String street;
	private String city;
	private StateEnum state;
	private String zip;
	private CountryCodeEnum country;
	private String phoneNumber;
	// End DB Fields
	
	@OneToMany(fetch= FetchType.LAZY)
	private List<CarRental> carRentals;
	
	public String getFullName() {
		return this.fullName;
	}
	
	public List<CarRental> getCarRentals() {
		return carRentals;
	}

	public void setCarRentals(List<CarRental> carRentals) {
		this.carRentals = carRentals;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(StateEnum state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setCountry(CountryCodeEnum country) {
		this.country = country;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreet() {
		return this.street;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public StateEnum getState() {
		return this.state;
	}
	
	public String getZip() {
		return this.zip;
	}
	
	public CountryCodeEnum getCountry() {
		return this.country;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public String getAddress() {
		return String.join(", ", this.street, this.city, String.valueOf(this.state), String.valueOf(this.zip), String.valueOf(this.country));
	}
	
	public Customer() {}
	
	public Customer(String userName, String password,
			String fullName, String street, String city, StateEnum state, String zip, CountryCodeEnum country, String phoneNumber) {
		super(userName, password, UserRoleEnum.Customer);
		this.fullName = fullName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public void print() {
		
	}
}