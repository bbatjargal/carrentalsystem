package mum.mpp.carrental.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;

@MappedSuperclass
public abstract class Vehicle {
	
	
	// DB Fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long vehicleId;
	
	@Enumerated(EnumType.STRING)
	private CarColorEnum color;

	private String description;
	private double baseRentCost;
	
	private VehicleStatusEnum status;
	// End DB Fields
	
	public Vehicle() {	}
	
	public Long getVehicleId() {
		return this.vehicleId;
	}
	
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public CarColorEnum getColor() {
		return this.color;
	}	
	
	public VehicleStatusEnum getStatus() {
		return status;
	}

	public String getDescription() {
		return this.description;
	}
	
	public double getBaseRentCost() {
		return this.baseRentCost;
	}	
	
	public void setColor(CarColorEnum color) {
		this.color = color;
	}

	public void setBaseRentCost(double baseRentCost) {
		this.baseRentCost = baseRentCost;
	}

	public void setStatus(VehicleStatusEnum status) {
		this.status = status;
	}

	public VehicleStatusEnum getVehicleStatus() {
		return this.status;
	}	
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Vehicle(CarColorEnum color, double baseRentCost, VehicleStatusEnum status) {
		this.color = color;
		this.baseRentCost = baseRentCost;
		this.status = status;
	}
	
	public abstract double calculateRentCost();
}