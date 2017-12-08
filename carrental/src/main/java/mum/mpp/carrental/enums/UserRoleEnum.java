package mum.mpp.carrental.enums;

public enum UserRoleEnum {
	System(1),
	Customer(2);
	
	private int role;
	
	public int getRole() {
		return this.role;
	}
	
	UserRoleEnum(int role) {
		this.role = role;
	}
}