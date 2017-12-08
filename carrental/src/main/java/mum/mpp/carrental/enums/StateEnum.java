package mum.mpp.carrental.enums;

public enum StateEnum {
	CALIFORNIA("CA"),
	IOWA("IA"),
	MARYLAND("MD");
	
	private String state;
	
	public String getCountryCode() {
		return this.state;
	}
	
	StateEnum(String state) {
		this.state = state;
	}
}
