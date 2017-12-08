package mum.mpp.carrental.enums;

public enum VehicleStatusEnum {
	Available(1),
	Rented(2),
	Unavalaible(3);
	
	private int status;
	
	public int getStatus() {
		return this.status;
	}
	
	VehicleStatusEnum(int status){
		this.status = status;
	}
}
