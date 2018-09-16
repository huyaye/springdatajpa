package com.example.demo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "account")
@Table
public class Account {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	private String password;

	@OneToMany(mappedBy = "owner")
	private Set<Study> studies = new HashSet<>();

	@Temporal(TemporalType.TIME)
	private Date created = new Date();

	@Transient
	private String yes;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "home_street")) })
	private Address address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	public void addStudy(Study study) {
		this.getStudies().add(study);
		study.setOwner(this);
	}

	public void removeStudy(Study study) {
		this.getStudies().remove(study);
		study.setOwner(null);
	}

}
