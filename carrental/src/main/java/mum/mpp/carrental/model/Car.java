package mum.mpp.carrental.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;

@Entity(name = "Cars")
public class Car extends Vehicle {
	// DB Fields
	
	private String licenseNumber;
	// End DB Fields
	
	@OneToMany(fetch= FetchType.LAZY)
	private List<CarRental> carRentals;
	
	public Car() {}
	
	public String getLicenseNumber() {
		return this.licenseNumber;
	}
	
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Car(CarColorEnum color, double baseRentCost, VehicleStatusEnum status, String licenseNumber) {
		super(color, baseRentCost, status);
		this.licenseNumber = licenseNumber;
	}

	@Override
	public double calculateRentCost() {
		return this.getBaseRentCost();
	}
}
