package ru.terra.activitystore.db.entity;
// Generated 09.07.2012 18:44:53 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cell generated by hbm2java
 */
@Entity
@Table(name="cell"
    ,catalog="activitystore"
)
public class Cell  implements java.io.Serializable {


     private Integer id;
     private Card card;
     private Vlist vlist;
     private int type;
     private String val;
     private String comment;
     private Date creationDate;
     private Date updateDate;

    public Cell() {
    }

	
    public Cell(Card card, int type, Date creationDate) {
        this.card = card;
        this.type = type;
        this.creationDate = creationDate;
    }
    public Cell(Card card, Vlist vlist, int type, String val, String comment, Date creationDate, Date updateDate) {
       this.card = card;
       this.vlist = vlist;
       this.type = type;
       this.val = val;
       this.comment = comment;
       this.creationDate = creationDate;
       this.updateDate = updateDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="card_id", nullable=false)
    public Card getCard() {
        return this.card;
    }
    
    public void setCard(Card card) {
        this.card = card;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="list_id")
    public Vlist getVlist() {
        return this.vlist;
    }
    
    public void setVlist(Vlist vlist) {
        this.vlist = vlist;
    }
    
    @Column(name="type", nullable=false)
    public int getType() {
        return this.type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    @Column(name="val", length=512)
    public String getVal() {
        return this.val;
    }
    
    public void setVal(String val) {
        this.val = val;
    }
    
    @Column(name="comment", length=200)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
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




}


