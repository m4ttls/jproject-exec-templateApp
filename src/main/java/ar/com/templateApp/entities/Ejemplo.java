package ar.com.templateApp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.curcico.jproject.core.entities.TimeRangeEntity;


/** Entidad que registra las noticias.
 * @author Ing. Alejandro Daniel Curci (acurci@gmail.com)
 *
 */
@Entity
@Table(name = "TBL_EJEMPLO")
@Where(clause="DELETED IS NULL")
@SQLDelete(sql="UPDATE TBL_EJEMPLO SET DELETED = CURRENT_TIMESTAMP WHERE ID = ? AND VERSION = ?")
public class Ejemplo extends TimeRangeEntity {

	private String codigo;
	private String descripcion;
	
	public Ejemplo() {
		super();
	}
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)	
	public Integer getId() {
		return super.id;
	}

	@Column(name = "CODIGO", nullable=false, length=10, unique=true)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "CODIGO", nullable=true, length=250, unique=false)
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
