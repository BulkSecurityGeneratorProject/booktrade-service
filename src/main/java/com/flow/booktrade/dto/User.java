package com.flow.booktrade.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	private Long id;
	private String login;
	private String email;
	private String name;
	@JsonIgnore
	private String password;
	private Location location;
	@JsonIgnore
	private Boolean activated;
	@JsonIgnore
	private UserRole role;
	private String langKey;
	private String avatar;
	private String bio;
	private Boolean pushNotification;
	private String databaseToken;
	
	public User(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Boolean isActivated(){
		return activated;
	}
	
	public void setActivated(Boolean activated){
		this.activated = activated;
	}
	
	public UserRole getRole(){
		return role;
	}
	
	public void setRole(UserRole role){
		this.role = role;
	}
	
	public String getLangKey(){
		return langKey;
	}
	
	public void setLangKey(String langKey){
		this.langKey = langKey;
	}
	
	public String getAvatar(){
		return avatar;
	}
	
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	
	public void setBio(String bio){
		this.bio = bio;
	}
	
	public String getBio(){
		return bio;
	}

	public Boolean getPushNotification() {
		return pushNotification;
	}
	
	public void setDatabaseToken(String databaseToken){
		this.databaseToken = databaseToken;
	}
	
	public String getDatabaseToken(){
		return databaseToken;
	}

	public void setPushNotification(Boolean pushNotification) {
		this.pushNotification = pushNotification;
	}
	
}

