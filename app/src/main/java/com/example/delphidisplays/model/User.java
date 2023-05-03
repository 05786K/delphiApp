package com.example.delphidisplays.model;

public class User {
			
	String uniqueID;
	String fullName; 
	String phone; 
	String email; 
	String password; 
	String beef; 
	String chicken; 
	String spicy;
	
	/**
	 * @param uniqueID
	 * @param fullName
	 * @param phone
	 * @param email
	 * @param password
	 * @param beef
	 * @param chicken
	 * @param spicy
	 */
	
	public User(String uniqueID, String fullName, String phone, String email, String password, String beef,
			String chicken, String spicy) {
		this.uniqueID = uniqueID;
		this.fullName = fullName;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.beef = beef;
		this.chicken = chicken;
		this.spicy = spicy;
	} 
	
	
	public User() {
		
	}


	//setters and getters
	public String getUniqueID() {
		return uniqueID;
	}


	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getBeef() {
		return beef;
	}


	public void setBeef(String beef) {
		this.beef = beef;
	}


	public String getChicken() {
		return chicken;
	}


	public void setChicken(String chicken) {
		this.chicken = chicken;
	}


	public String getSpicy() {
		return spicy;
	}


	public void setSpicy(String spicy) {
		this.spicy = spicy;
	}


	//to String
	@Override
	public String toString() {
		return "User [uniqueID=" + uniqueID + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email
				+ ", password=" + password + ", beef=" + beef + ", chicken=" + chicken + ", spicy=" + spicy + "]";
	}
	
	
	

	
	
}
