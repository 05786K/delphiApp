package com.example.delphidisplays.model;

public class User {

	int user_id;
	String first_name, last_name, email, password;
	String calories, total_fat, saturated_fat, sodium, carbohydrates, sugars, protein;
	/**
	 * @param user_id
	 * @param first_name
	 * @param last_name
	 * @param email
	 * @param password
	 * @param calories
	 * @param total_fat
	 * @param saturated_fat
	 * @param sodium
	 * @param carbohydrates
	 * @param sugars
	 * @param protein
	 */

	//constructor
	public User(int user_id, String first_name, String last_name, String email, String password, String calories,
				String total_fat, String saturated_fat, String sodium, String carbohydrates, String sugars,
				String protein) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.calories = calories;
		this.total_fat = total_fat;
		this.saturated_fat = saturated_fat;
		this.sodium = sodium;
		this.carbohydrates = carbohydrates;
		this.sugars = sugars;
		this.protein = protein;
	}
	//default constructor
	public User() {

	}


	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	public String getCalories() {
		return calories;
	}
	public void setCalories(String calories) {
		this.calories = calories;
	}
	public String getTotal_fat() {
		return total_fat;
	}
	public void setTotal_fat(String total_fat) {
		this.total_fat = total_fat;
	}
	public String getSaturated_fat() {
		return saturated_fat;
	}
	public void setSaturated_fat(String saturated_fat) {
		this.saturated_fat = saturated_fat;
	}
	public String getSodium() {
		return sodium;
	}
	public void setSodium(String sodium) {
		this.sodium = sodium;
	}
	public String getCarbohydrates() {
		return carbohydrates;
	}
	public void setCarbohydrates(String carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	public String getSugars() {
		return sugars;
	}
	public void setSugars(String sugars) {
		this.sugars = sugars;
	}
	public String getProtein() {
		return protein;
	}
	public void setProtein(String protein) {
		this.protein = protein;
	}


	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", first_name=" + first_name + ", last_name=" + last_name + ", email="
				+ email + ", password=" + password + ", calories=" + calories + ", total_fat=" + total_fat
				+ ", saturated_fat=" + saturated_fat + ", sodium=" + sodium + ", carbohydrates=" + carbohydrates
				+ ", sugars=" + sugars + ", protein=" + protein + "]";
	}




}
