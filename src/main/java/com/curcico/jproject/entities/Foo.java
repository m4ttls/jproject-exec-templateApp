package com.curcico.jproject.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.curcico.jproject.core.entities.TimeRangeEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** Example entity
 * @author Ing. Alejandro Daniel Curci (acurci@gmail.com)
 *
 */
@Entity
@ApiModel(	value="Foo", 
			description="Foo entity", 
			reference="Foo reference", 
			parent=TimeRangeEntity.class)
@Table(name = "TBL_FOO")
@Where(clause="DELETED IS NULL")
@SQLDelete(sql="UPDATE TBL_FOO SET DELETED = CURRENT_TIMESTAMP WHERE ID = ? AND VERSION = ?")
public class Foo extends TimeRangeEntity {

	enum Type {
	    EXAMPLE_ONE,
	    EXAMPLE_TWO;
	}
	
	private String code;
	private String description;
	private Type   type;
	private Collection<Bar> bars;
	
	public Foo() {
		super();
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	@ApiModelProperty(value="Foo's primary key", required=false, position=0)
	public Integer getId() {
		return super.id;
	}

	@Column(name = "CODE", nullable=false, length=10, unique=true)
	@ApiModelProperty(value="Code", required=true, notes="Unique Code", position=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "DESCRIPTION", nullable=true, length=250, unique=false)
	@ApiModelProperty(value="Foo's description", required=true, position=2)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE", nullable=false)
	@ApiModelProperty(value="Foo's type", required=true, position=3)
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foo")
	@ApiModelProperty(value="Foo's type", required=true, position=3)
	@JsonManagedReference(value="RELATION_FOO_BAR")
	public Collection<Bar> getBars() {
		return bars;
	}
	
	public void setBars(Collection<Bar> bars) {
		this.bars = bars;
	}
	
	@Override
	public void extractMutableValues(Object newObject) {
		super.extractMutableValues(newObject);
		Foo other = (this.getClass().cast(newObject));
		this.setCode(other.getCode());
		this.setDescription(other.getDescription());
		this.setType(other.getType());
	}
	
}