package com.csvparser.entity;

public class Enrollee {
	private String userId;
	private String firstName;
	private String lastName;
	private int version;
	private String insuranceCompany;

	public Enrollee(String userId, String firstName, String lastName, int version, String insuranceCompany) {
		this.setUserId(userId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setVersion(version);
		this.setInsuranceCompany(insuranceCompany);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

}
