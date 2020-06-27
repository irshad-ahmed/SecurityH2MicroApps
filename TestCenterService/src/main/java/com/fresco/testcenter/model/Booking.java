package com.fresco.testcenter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public long hospitalId;
	public long testCenterId;
	public String user;
	public Date date;
	public String status;
	public Booking(long hospitalId, String user, Date date) {
		super();
		this.hospitalId = hospitalId;
		this.user = user;
		this.date = date;
		this.status = "PENDING";
	}
	public Booking() {
		super();
	}
	public Long getId() {
		return id;
	}
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTestCenterId() {
		return testCenterId;
	}
	public void setTestCenterId(long testCenterId) {
		this.testCenterId = testCenterId;
	}
	@Override
	public String toString() {
		return "Booking [id=" + id + ", hospitalId=" + hospitalId + ", user=" + user + ", date=" + date + ", status="
				+ status + ", testCenterId=" + testCenterId + "]";
	}
}