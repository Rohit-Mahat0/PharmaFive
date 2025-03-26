package com.pharmafive.model;

import javax.persistence.*;

@Entity
@Table(name = "registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "mobile_number", nullable = false, length = 15)
    private String mobileNumber;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "organisation_name", nullable = false, length = 100)
    private String organisationName;

    @Column(nullable = false, length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.Pending;
    
    @Column(nullable = false, length = 20)
    private String role = "User"; // Default role

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public enum Status {
        Active, Inactive, Pending
    }

    // Constructors
    public Registration() {}

    public Registration(Long sno, String name, String mobileNumber, String email,
                        String organisationName, String password, Status status) {
        this.sno = sno;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.organisationName = organisationName;
        this.password = password;
        this.status = status;
    }

    // Getters and Setters
    public Long getSno() {
        return sno;
    }

    public void setSno(Long sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
