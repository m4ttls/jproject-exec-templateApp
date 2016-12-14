package com.curcico.jproject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.curcico.jproject.core.entities.TimeRangeEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "User", description = "User entity", reference = "User reference")
@Table(name = "VWR_USERS")
@Where(clause = "DELETED IS NULL")
@SQLDelete(sql = "UPDATE VWR_USERS SET DELETED = CURRENT_TIMESTAMP WHERE ID = ?")
public class User extends TimeRangeEntity{

	private String username;
	private String password;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@ApiModelProperty(value = "User's primary key", required = false, position = 0)
	public Integer getId() {
		return super.id;
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

}
