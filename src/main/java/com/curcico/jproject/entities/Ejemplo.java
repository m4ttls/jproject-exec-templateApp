package com.curcico.jproject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.curcico.jproject.core.entities.TimeRangeEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** Entidad que registra las noticias.
 * @author Ing. Alejandro Daniel Curci (acurci@gmail.com)
 *
 */
@Entity
@ApiModel(value="value", description="description", reference="reference", parent=TimeRangeEntity.class)
@Table(name = "TBL_EJEMPLO")
@Where(clause="DELETED IS NULL")
@SQLDelete(sql="UPDATE TBL_EJEMPLO SET DELETED = CURRENT_TIMESTAMP WHERE ID = ? AND VERSION = ?")
public class Ejemplo extends TimeRangeEntity {

	enum Tipo {
	    EXAMPLE_ONE,
	    EXAMPLE_TWO;
	}
	
	private String codigo;
	private String descripcion;
	private Tipo   tipo;
	
	public Ejemplo() {
		super();
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	@ApiModelProperty(value="Clave primaria de la entidad Ejemplo", required=false, position=0)
	public Integer getId() {
		return super.id;
	}

	@Column(name = "CODIGO", nullable=false, length=10, unique=true)
	@ApiModelProperty(value="Codigo", required=true, notes="Codigo unico de la entidad", position=1)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "DESCRIPCION", nullable=true, length=250, unique=false)
	@ApiModelProperty(value="Descripcion de la entidad", required=true, position=2)
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TIPO", nullable=false)
	@ApiModelProperty(value="Propiedad correspondiente a un Enum", required=true, position=3)
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public void extractMutableValues(Object newObject) {
		super.extractMutableValues(newObject);
		Ejemplo other = (this.getClass().cast(newObject));
		this.setCodigo(other.getCodigo());
		this.setDescripcion(other.getDescripcion());
		this.setTipo(other.getTipo());
	}
	
}