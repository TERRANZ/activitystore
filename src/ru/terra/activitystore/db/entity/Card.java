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
 * Card generated by hbm2java
 */
@Entity
@Table(name="card"
    ,catalog="activitystore"
)
public class Card  implements java.io.Serializable {


     private Integer id;
     private Integer blockId;
     private Integer templateId;
     private String name;
     private Date creationDate;
     private Date updateDate;
     private Set<Cell> cells = new HashSet<Cell>(0);
     private Set<Template> templates = new HashSet<Template>(0);

    public Card() {
    }

	
    public Card(String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }
    public Card(Integer blockId, Integer templateId, String name, Date creationDate, Date updateDate, Set<Cell> cells, Set<Template> templates) {
       this.blockId = blockId;
       this.templateId = templateId;
       this.name = name;
       this.creationDate = creationDate;
       this.updateDate = updateDate;
       this.cells = cells;
       this.templates = templates;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="block_id")
    public Integer getBlockId() {
        return this.blockId;
    }
    
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
    
    @Column(name="template_id")
    public Integer getTemplateId() {
        return this.templateId;
    }
    
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
    
    @Column(name="name", nullable=false, length=512)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="card")
    public Set<Cell> getCells() {
        return this.cells;
    }
    
    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="card")
    public Set<Template> getTemplates() {
        return this.templates;
    }
    
    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }




}


