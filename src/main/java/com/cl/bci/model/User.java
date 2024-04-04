package com.cl.bci.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class User.
 */
@Entity
@Table(name = "user")

public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7191719154491037266L;

	@Id
	@GeneratedValue
	@ApiModelProperty(hidden = true)
	@Column(name = "UUID", unique = true, length = Integer.MAX_VALUE)
	private Long uuid;

	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@ElementCollection
	@CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "UUID"))
	private List<Phones> phones;

	@ApiModelProperty(hidden = true)
	private String created;

	@ApiModelProperty(hidden = true)
	private String modified;

	@ApiModelProperty(hidden = true)
	private String last_login;

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phones> getPhones() {
		return phones;
	}

	public void setPhones(List<Phones> phones) {
		this.phones = phones;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getLast_login() {
		return last_login;
	}

	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

}
