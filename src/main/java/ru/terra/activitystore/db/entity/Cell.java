/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author terranz
 */
@Entity
@Table(name = "cell", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Cell.findAll", query = "SELECT c FROM Cell c"),
    @NamedQuery(name = "Cell.findById", query = "SELECT c FROM Cell c WHERE c.id = :id"),
    @NamedQuery(name = "Cell.findByType", query = "SELECT c FROM Cell c WHERE c.type = :type"),
    @NamedQuery(name = "Cell.findByVal", query = "SELECT c FROM Cell c WHERE c.val = :val"),
    @NamedQuery(name = "Cell.findByComment", query = "SELECT c FROM Cell c WHERE c.comment = :comment"),
    @NamedQuery(name = "Cell.findByCreationDate", query = "SELECT c FROM Cell c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "Cell.findByUpdateDate", query = "SELECT c FROM Cell c WHERE c.updateDate = :updateDate")
})
public class Cell implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type", nullable = false)
    private int type;
    @Column(name = "val", length = 512)
    private String val;
    @Column(name = "comment", length = 200)
    private String comment;
    @Basic(optional = false)
    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @ManyToMany(mappedBy = "cellList")
    private List<Card> cardList;
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @ManyToOne
    private Vlist listId;

    public Cell()
    {
    }

    public Cell(Integer id)
    {
        this.id = id;
    }

    public Cell(Integer id, int type, Date creationDate)
    {
        this.id = id;
        this.type = type;
        this.creationDate = creationDate;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getVal()
    {
        return val;
    }

    public void setVal(String val)
    {
        this.val = val;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    @XmlTransient
    public List<Card> getCardList()
    {
        return cardList;
    }

    public void setCardList(List<Card> cardList)
    {
        this.cardList = cardList;
    }

    public Vlist getListId()
    {
        return listId;
    }

    public void setListId(Vlist listId)
    {
        this.listId = listId;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cell))
        {
            return false;
        }
        Cell other = (Cell) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ru.terra.activitystore.db.entity.Cell[ id=" + id + " ]";
    }
    
}
