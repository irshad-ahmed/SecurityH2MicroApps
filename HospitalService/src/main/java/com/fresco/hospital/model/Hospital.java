package com.fresco.hospital.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	public String name;
	public String location;
	@ElementCollection(targetClass=Long.class)
	public List<Long> testCenters;
	public Hospital(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	public Hospital() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<Long> getTestCenters() {
		return testCenters;
	}
	public void setTestCenters(List<Long> testCenters) {
		this.testCenters = testCenters;
	}
	@Override
	public String toString() {
		return "Hospital [id=" + id + ", name=" + name + ", location=" + location + ", testCenters=" + testCenters + "]";
	}
}