/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author terranz
 */
@Entity
@Table(name = "template", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Template.findAll", query = "SELECT t FROM Template t"),
    @NamedQuery(name = "Template.findById", query = "SELECT t FROM Template t WHERE t.id = :id"),
    @NamedQuery(name = "Template.findByName", query = "SELECT t FROM Template t WHERE t.name = :name"),
    @NamedQuery(name = "Template.findByCreationDate", query = "SELECT t FROM Template t WHERE t.creationDate = :creationDate"),
    @NamedQuery(name = "Template.findByUpdateDate", query = "SELECT t FROM Template t WHERE t.updateDate = :updateDate")
})
public class Template implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 512)
    private String name;
    @Basic(optional = false)
    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @JoinColumn(name = "card", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Card card;

    public Template()
    {
    }

    public Template(Integer id)
    {
        this.id = id;
    }

    public Template(Integer id, String name, Date creationDate)
    {
        this.id = id;
        this.name = name;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public Card getCard()
    {
        return card;
    }

    public void setCard(Card card)
    {
        this.card = card;
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
        if (!(object instanceof Template))
        {
            return false;
        }
        Template other = (Template) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ru.terra.activitystore.db.entity.Template[ id=" + id + " ]";
    }
    
}
