/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "vlist", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Vlist.findAll", query = "SELECT v FROM Vlist v"),
		@NamedQuery(name = "Vlist.findById", query = "SELECT v FROM Vlist v WHERE v.id = :id"),
		@NamedQuery(name = "Vlist.findByCreationDate", query = "SELECT v FROM Vlist v WHERE v.creationDate = :creationDate"),
		@NamedQuery(name = "Vlist.findByUpdateDate", query = "SELECT v FROM Vlist v WHERE v.updateDate = :updateDate") })
public class Vlist implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "creation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "listId")
	private List<ListVal> listValList;
	@OneToMany(mappedBy = "listId")
	private List<Cell> cellList;
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vlist() {
	}

	public Vlist(Integer id) {
		this.id = id;
	}

	public Vlist(Integer id, Date creationDate) {
		this.id = id;
		this.creationDate = creationDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@XmlTransient
	public List<ListVal> getListValList() {
		return listValList;
	}

	public void setListValList(List<ListVal> listValList) {
		this.listValList = listValList;
	}

	@XmlTransient
	public List<Cell> getCellList() {
		return cellList;
	}

	public void setCellList(List<Cell> cellList) {
		this.cellList = cellList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Vlist)) {
			return false;
		}
		Vlist other = (Vlist) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ru.terra.activitystore.db.entity.Vlist[ id=" + id + " ]";
	}

}
