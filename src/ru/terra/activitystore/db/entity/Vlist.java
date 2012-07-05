package ru.terra.activitystore.db.entity;
// Generated 05.07.2012 13:20:05 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Vlist generated by hbm2java
 */
@Entity
@Table(name="vlist"
    ,catalog="activitystore"
)
public class Vlist  implements java.io.Serializable {


     private Integer id;
     private Date creationDate;
     private Date updateDate;
     private Set<ListVal> listVals = new HashSet<ListVal>(0);
     private Set<Cell> cells = new HashSet<Cell>(0);

    public Vlist() {
    }

	
    public Vlist(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Vlist(Date creationDate, Date updateDate, Set<ListVal> listVals, Set<Cell> cells) {
       this.creationDate = creationDate;
       this.updateDate = updateDate;
       this.listVals = listVals;
       this.cells = cells;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date", nullable=false, length=19)
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="update_date", length=19)
    public Date getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="vlist")
    public Set<ListVal> getListVals() {
        return this.listVals;
    }
    
    public void setListVals(Set<ListVal> listVals) {
        this.listVals = listVals;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="vlist")
    public Set<Cell> getCells() {
        return this.cells;
    }
    
    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }




}


