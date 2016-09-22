package com.curcico.jproject.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.curcico.jproject.core.entities.TimeRangeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** Example entity
 * @author Ing. Alejandro Daniel Curci (acurci@gmail.com)
 *
 */
@Entity
@ApiModel(	value="Bar", 
			description="Bar entity", 
			reference="Bar reference", 
			parent=TimeRangeEntity.class)
@Table(name = "TBL_BAR")
@Where(clause="DELETED IS NULL")
@SQLDelete(sql="UPDATE TBL_BAR SET DELETED = CURRENT_TIMESTAMP WHERE ID = ? AND VERSION = ?")
public class Bar extends TimeRangeEntity {
	
	private Foo foo;
	private Date date;
	private String detail;
	
	public Bar() {
		super();
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	@ApiModelProperty(value="Bar's primary key", required=false, position=0)
	public Integer getId() {
		return super.id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "FOO_ID", nullable = false)
	@JsonBackReference(value="RELATION_FOO_BAR")
	public Foo getFoo() {
		return foo;
	}
	
	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	@Column(name = "DATE", nullable=false)
	@ApiModelProperty(value="Date", required=true, position=1)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name = "DETAIL", nullable=false, length=250, unique=false)
	@ApiModelProperty(value="Detail", required=true, position=2)
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@Override
	public void extractMutableValues(Object newObject) {
		super.extractMutableValues(newObject);
		Bar other = (this.getClass().cast(newObject));
		this.setDate(other.getDate());
		this.setDetail(other.getDetail());
	}
	
}