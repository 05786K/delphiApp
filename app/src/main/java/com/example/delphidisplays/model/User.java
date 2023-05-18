package com.example.delphidisplays.model;


import java.util.ArrayList;

public class User {


	/**
	 * @param userId
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

	private String userId;
	private String first_name, last_name;

	private String email;

	private String password;
	private Preferences preferences;
	ArrayList<String> filters;

	public User(String first_name,
				String last_name,
				String email,
				String password,
				Preferences preferences,
				ArrayList<String> filters) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.preferences = preferences;
		this.filters = filters;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public ArrayList<String> getFilters() {
		return filters;
	}

	public void setFilters(ArrayList<String> filters) {
		this.filters = filters;
	}


}
