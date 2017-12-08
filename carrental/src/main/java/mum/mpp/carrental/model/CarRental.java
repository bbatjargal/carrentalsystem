package mum.mpp.carrental.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import mum.mpp.carrental.enums.CarColorEnum;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class CarRental {
	// DB Fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long carRentalId;
	
	private LocalDate rentDate;
	private LocalDate expectedReturnDate;
	private LocalDate returnDate;
	private double expectedRentCost;
	private double penaltyCost;
	// End DB Fields
	
	@OneToOne(fetch = FetchType.LAZY)
	private Customer customer;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Car car;
	
	public CarRental() {}
	
	public Long getCarRentalId() {
		return this.carRentalId;
	}
	
	public LocalDate getRentDate() {
		return this.rentDate;
	}
	
	public LocalDate getExpectedReturnDate() {
		return this.expectedReturnDate;
	}
	
	public LocalDate getActualReturnDate() {
		return this.returnDate;
	}
	
	public String getCarLicenseNumber() {
		return this.car != null ? this.car.getLicenseNumber() : "";
	}
	public CarColorEnum getCarColor() {
		return this.car != null ? this.car.getColor() : null;
	}

	public String getCustomerName() {
		return this.customer != null ? this.customer.getFullName() : "";
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public Car getCar() {
		return this.car;
	}
	
	public CarRental(Customer customer, Car car, LocalDate rentDate, LocalDate returnDate) {
		this.customer = customer;
		this.car = car;
		this.rentDate = rentDate;
		this.expectedReturnDate = returnDate;
		this.expectedRentCost = this.calculateExpectedRentCost();
	}
	
	public int calculateExpectedRentDates() {
		return (int) DAYS.between(rentDate, expectedReturnDate);
	}
	
	public void returnCar(LocalDate returnDate) {
		this.returnDate = returnDate;
		this.penaltyCost = this.calculatePenaltyCost();
	}
	
	private double calculateExpectedRentCost() {
		return this.car.calculateRentCost() * this.calculateExpectedRentDates();
	}
	
	private double calculatePenaltyCost() {
		if(this.returnDate != null && this.returnDate.isAfter(this.expectedReturnDate)) {
			int overdueDays = (int) DAYS.between(expectedReturnDate, returnDate);
			return overdueDays * this.car.calculateRentCost();
		}
		
		return 0;
	}
	
	public double getTotalRentCost() {
		return this.expectedRentCost + this.penaltyCost;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public double getExpectedRentCost() {
		return expectedRentCost;
	}

	public void setExpectedRentCost(double expectedRentCost) {
		this.expectedRentCost = expectedRentCost;
	}

	public double getPenaltyCost() {
		return penaltyCost;
	}

	public void setPenaltyCost(double penaltyCost) {
		this.penaltyCost = penaltyCost;
	}

	public void setCarRentalId(Long carRentalId) {
		this.carRentalId = carRentalId;
	}

	public void setRentDate(LocalDate rentDate) {
		this.rentDate = rentDate;
	}

	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setCar(Car car) {
		this.car = car;
	}	
}
