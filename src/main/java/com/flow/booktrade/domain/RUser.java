package com.flow.booktrade.domain;

import com.flow.booktrade.config.Constants;
import com.flow.booktrade.dto.Platform;
import com.flow.booktrade.dto.TextLength;
import com.flow.booktrade.dto.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.time.ZonedDateTime;

/**
 * A user.
 */
@Entity
@Table(name = "booktrade_user", indexes={
		@Index(name="user_location_idx", columnList="latitude, longitude"),
		@Index(name="user_city_idx", columnList="city")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RUser extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash",length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date", nullable = true)
    private ZonedDateTime resetDate = null;
    
    @Column(name="role_type")
    private UserRole role;
    
    @Column(name="avatar")
    private String avatar;

    /**
     * iOS unique device token
     */
    @Column(name="device_token")
    private String deviceToken;
    
    /**
     * Database token to access firebase realtime data
     */
    @Column(name="database_token", length=TextLength.FIREBASE_AUTH_LENGTH)
    private String databaseToken;
    
    @Enumerated(EnumType.STRING)
    @Column(name="platform")
    private Platform platform;   
    
    /**
     * User location
     */
    @Embedded
    private RLocation location;
    
    /**
     * true = user enables push notifications
     */
    @Column(name="push_notification")
    private Boolean pushNotification = true;
    
    @Column(name="bio")
    private String bio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    //Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = login.toLowerCase(Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
       return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
       this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public UserRole getUserRole(){
    	return role;
    }
    
    public void setUserRole(UserRole role){
    	this.role = role;
    }
    
    public String getAvatar(){
    	return avatar;
    }
    
    public void setAvatar(String avatar){
    	this.avatar = avatar;
    }
    
    public RLocation getLocation(){
    	return location;
    }
    
    public void setLocation(RLocation location){
    	this.location = location;
    }
    
    public Platform getPlatform(){
    	return platform;
    }
    
    public void setPlatform(Platform platform){
    	this.platform = platform;
    }
    
    public String getDeviceToken(){
    	return deviceToken;
    }
    
    public void setDeviceToken(String deviceToken){
    	this.deviceToken = deviceToken;
    }
    
    public void setBio(String bio){
    	this.bio = bio;
    }
    
    public String getBio(){
    	return bio;
    }
    
    public Boolean getPushNotification(){
    	return pushNotification;
    }
    
    public void setPushNotification(Boolean pushNotification){
    	this.pushNotification = pushNotification;
    }
    
    public String getDatabaseToken(){
    	return databaseToken;
    }
    
    public void setDatabaseToken(String databaseToken){
    	this.databaseToken = databaseToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RUser user = (RUser) o;

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
