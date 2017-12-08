package mum.mpp.carrental.enums;

public enum CountryCodeEnum {
	CANADA("CAN"),
	US("US");
	
	private String code;
	
	public String getCountryCode() {
		return this.code;
	}
	
	CountryCodeEnum(String code) {
		this.code = code;
	}
}