package com.fresco.patient.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ApplicationUser {
	@Id
	public String mobile;
	public String password;
	public String fullname;
	public String location;
	public ApplicationUser(String mobile, String password, String fullname, String location) {
		super();
		this.mobile = mobile;
		this.password = password;
		this.fullname = fullname;
		this.location = location;
	}
	public ApplicationUser() {
		super();
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "ApplicationUser [mobile=" + mobile + ", password=" + password + ", fullname=" + fullname + ", location="
				+ location + "]";
	}
}