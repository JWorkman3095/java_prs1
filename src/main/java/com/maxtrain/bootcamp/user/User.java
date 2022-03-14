package com.maxtrain.bootcamp.user;

import javax.persistence.*;

@Entity
//unique
@Table(uniqueConstraints=@UniqueConstraint(name="UIDX_code", columnNames={"userName"}))
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition="int")
	private int id;
	@Column(length=30, nullable=false)
	private String username;
	@Column(length=30, nullable=false)
	private String password;
	@Column(length=30, nullable=false)
	private String firstname;
	@Column(length=30, nullable=false)
	private String lastName;
	@Column(length=12, nullable=true)
	private String phone;
	@Column(length=225, nullable=true)
	private String email;
	private boolean isReview;
	private boolean isAdmin;
	
	public User () {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		firstname = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		email = email;
	}

	public boolean isIsReview() {
		return isReview;
	}

	public void setIsReview(boolean isReview) {
		isReview = isReview;
	}

	public boolean isIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		isAdmin = isAdmin;
	}

	
}
