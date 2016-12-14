package com.curcico.jproject.entities;

import java.math.BigDecimal;
import java.util.Date;

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
@ApiModel(value = "Document", description = "Document entity", reference = "Document reference", parent = TimeRangeEntity.class)
@Table(name = "VWR_DOCUMENTS")
@Where(clause = "DELETED IS NULL")
@SQLDelete(sql = "UPDATE VWR_DOCUMENTS SET DELETED = CURRENT_TIMESTAMP WHERE ID = ? AND VERSION = ?")
public class Document extends TimeRangeEntity {

	// Atributos
	private String key; // atributo seteado en caliente para mostrar en la
						// grilla, calculando el keyfield.
	private Integer id_parents;
	private String stringOne;
	private String stringTwo;
	private String stringThree;
	private String stringFour;
	private String stringFive;
	private String stringSix;
	private String stringSeven;
	private String stringEight;
	private String stringNine;
	private String stringTen;
	private String stringEleven;
	private Integer integerOne;
	private Integer integerTwo;
	private Integer integerThree;
	private Integer integerFour;
	private Integer integerFive;
	private Integer integerSix;
	private Integer integerSeven;
	private Integer integerEight;
	private Date dateOne;
	private Date dateTwo;
	private Boolean booleanOne;
	private Boolean booleanTwo;
	private BigDecimal decimalOne;
	private BigDecimal decimalTwo;
	private BigDecimal decimalThree;
	private BigDecimal decimalFour;
	private String geolocationOne;
	private String geolocationTwo;
	private String extension;
	private Integer versionFile;
	private String nameFileInFileSystem;

	@Column(length = 100, nullable = true)
	private String description;

	//private DocumentType documentType;

	private String patchId;

	private Integer validatestatus;

	private Integer cantSheets;

	private long sizefile;
	private Integer notIndexed = 0;

	// Se usa solo para la vista
	private boolean favourite = false;

	public Document() {
		super();
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@ApiModelProperty(value = "Document's primary key", required = false, position = 0)
	public Integer getId() {
		return super.id;
	}
	/*
	 * public DocumentType getDocumentType() { return documentType; }
	 * 
	 * public void setDocumentType(DocumentType documentType) {
	 * this.documentType = documentType; }
	 */

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getId_parents() {
		return id_parents;
	}

	public void setId_parents(Integer id_parents) {
		this.id_parents = id_parents;
	}

	public String getStringOne() {
		return stringOne;
	}

	public void setStringOne(String stringOne) {
		this.stringOne = stringOne;
	}

	public String getStringTwo() {
		return stringTwo;
	}

	public void setStringTwo(String stringTwo) {
		this.stringTwo = stringTwo;
	}

	public String getStringThree() {
		return stringThree;
	}

	public void setStringThree(String stringThree) {
		this.stringThree = stringThree;
	}

	public String getStringFour() {
		return stringFour;
	}

	public void setStringFour(String stringFour) {
		this.stringFour = stringFour;
	}

	public String getStringFive() {
		return stringFive;
	}

	public void setStringFive(String stringFive) {
		this.stringFive = stringFive;
	}

	public String getStringSix() {
		return stringSix;
	}

	public void setStringSix(String stringSix) {
		this.stringSix = stringSix;
	}

	public String getStringSeven() {
		return stringSeven;
	}

	public void setStringSeven(String stringSeven) {
		this.stringSeven = stringSeven;
	}

	public String getStringEight() {
		return stringEight;
	}

	public void setStringEight(String stringEight) {
		this.stringEight = stringEight;
	}

	public String getStringNine() {
		return stringNine;
	}

	public void setStringNine(String stringNine) {
		this.stringNine = stringNine;
	}

	public String getStringTen() {
		return stringTen;
	}

	public void setStringTen(String stringTen) {
		this.stringTen = stringTen;
	}

	public String getStringEleven() {
		return stringEleven;
	}

	public void setStringEleven(String stringEleven) {
		this.stringEleven = stringEleven;
	}

	public Integer getIntegerOne() {
		return integerOne;
	}

	public void setIntegerOne(Integer integerOne) {
		this.integerOne = integerOne;
	}

	public Integer getIntegerTwo() {
		return integerTwo;
	}

	public void setIntegerTwo(Integer integerTwo) {
		this.integerTwo = integerTwo;
	}

	public Integer getIntegerThree() {
		return integerThree;
	}

	public void setIntegerThree(Integer integerThree) {
		this.integerThree = integerThree;
	}

	public Integer getIntegerFour() {
		return integerFour;
	}

	public void setIntegerFour(Integer integerFour) {
		this.integerFour = integerFour;
	}

	public Integer getIntegerFive() {
		return integerFive;
	}

	public void setIntegerFive(Integer integerFive) {
		this.integerFive = integerFive;
	}

	public Integer getIntegerSix() {
		return integerSix;
	}

	public void setIntegerSix(Integer integerSix) {
		this.integerSix = integerSix;
	}

	public Integer getIntegerSeven() {
		return integerSeven;
	}

	public void setIntegerSeven(Integer integerSeven) {
		this.integerSeven = integerSeven;
	}

	public Integer getIntegerEight() {
		return integerEight;
	}

	public void setIntegerEight(Integer integerEight) {
		this.integerEight = integerEight;
	}

	public Date getDateOne() {
		return dateOne;
	}

	public void setDateOne(Date dateOne) {
		this.dateOne = dateOne;
	}

	public Date getDateTwo() {
		return dateTwo;
	}

	public void setDateTwo(Date dateTwo) {
		this.dateTwo = dateTwo;
	}

	public Boolean getBooleanOne() {
		return booleanOne;
	}

	public void setBooleanOne(Boolean booleanOne) {
		this.booleanOne = booleanOne;
	}

	public Boolean getBooleanTwo() {
		return booleanTwo;
	}

	public void setBooleanTwo(Boolean booleanTwo) {
		this.booleanTwo = booleanTwo;
	}

	public BigDecimal getDecimalOne() {
		return decimalOne;
	}

	public void setDecimalOne(BigDecimal decimalOne) {
		this.decimalOne = decimalOne;
	}

	public BigDecimal getDecimalTwo() {
		return decimalTwo;
	}

	public void setDecimalTwo(BigDecimal decimalTwo) {
		this.decimalTwo = decimalTwo;
	}

	public BigDecimal getDecimalThree() {
		return decimalThree;
	}

	public void setDecimalThree(BigDecimal decimalThree) {
		this.decimalThree = decimalThree;
	}

	public BigDecimal getDecimalFour() {
		return decimalFour;
	}

	public void setDecimalFour(BigDecimal decimalFour) {
		this.decimalFour = decimalFour;
	}

	public String getGeolocationOne() {
		return geolocationOne;
	}

	public void setGeolocationOne(String geolocationOne) {
		this.geolocationOne = geolocationOne;
	}

	public String getGeolocationTwo() {
		return geolocationTwo;
	}

	public void setGeolocationTwo(String geolocationTwo) {
		this.geolocationTwo = geolocationTwo;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Integer getVersionFile() {
		return versionFile;
	}

	public void setVersionFile(Integer versionFile) {
		this.versionFile = versionFile;
	}

	public String getNameFileInFileSystem() {
		return nameFileInFileSystem;
	}

	public void setNameFileInFileSystem(String nameFileInFileSystem) {
		this.nameFileInFileSystem = nameFileInFileSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
/*
	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
*/
	public String getPatchId() {
		return patchId;
	}

	public void setPatchId(String patchId) {
		this.patchId = patchId;
	}

	public Integer getValidatestatus() {
		return validatestatus;
	}

	public void setValidatestatus(Integer validatestatus) {
		this.validatestatus = validatestatus;
	}

	public Integer getCantSheets() {
		return cantSheets;
	}

	public void setCantSheets(Integer cantSheets) {
		this.cantSheets = cantSheets;
	}

	public long getSizefile() {
		return sizefile;
	}

	public void setSizefile(long sizefile) {
		this.sizefile = sizefile;
	}

	public Integer getNotIndexed() {
		return notIndexed;
	}

	public void setNotIndexed(Integer notIndexed) {
		this.notIndexed = notIndexed;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}
}
