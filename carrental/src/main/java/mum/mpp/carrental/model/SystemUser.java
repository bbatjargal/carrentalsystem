package mum.mpp.carrental.model;

import javax.persistence.Entity;

import mum.mpp.carrental.enums.UserRoleEnum;

@Entity(name = "SystemUsers")
public class SystemUser extends User {

	public SystemUser() {}
	public SystemUser(String userName, String password) {
		super(userName, password, UserRoleEnum.System);
	}

	@Override
	public void print() {
		
	}
}