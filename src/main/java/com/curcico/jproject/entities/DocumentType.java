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
@ApiModel(	value="DocumentType", 
			description="DocumentType entity", 
			reference="DocumentType reference", 
			parent=TimeRangeEntity.class)
@Table(name = "VWR_DOCUMENTTYPES")
@Where(clause="DELETED IS NULL")
@SQLDelete(sql="UPDATE VWR_DOCUMENTTYPES SET DELETED = CURRENT_TIMESTAMP WHERE ID = ? AND VERSION = ?")
public class DocumentType extends TimeRangeEntity {

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	@ApiModelProperty(value="DocumentType's primary key", required=false, position=0)
	public Integer getId() {
		return super.id;
	}

}
