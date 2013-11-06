/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author terranz
 */
@Entity
@Table(name = "block", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Block.findAll", query = "SELECT b FROM Block b"),
        @NamedQuery(name = "Block.findById", query = "SELECT b FROM Block b WHERE b.id = :id"),
        @NamedQuery(name = "Block.findByName", query = "SELECT b FROM Block b WHERE b.name = :name"),
        @NamedQuery(name = "Block.findByParent", query = "SELECT b FROM Block b WHERE b.parent = :parent"),
        @NamedQuery(name = "Block.findByCreationDate", query = "SELECT b FROM Block b WHERE b.creationDate = :creationDate"),
        @NamedQuery(name = "Block.findByUpdateDate", query = "SELECT b FROM Block b WHERE b.updateDate = :updateDate")})
public class Block implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic(optional = false)
    @Column(name = "parent", nullable = false)
    private int parent;
    @Basic(optional = false)
    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Block() {
    }

    public Block(Integer id) {
        this.id = id;
    }

    public Block(Integer id, String name, int parent, Date creationDate) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
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
        if (!(object instanceof Block)) {
            return false;
        }
        Block other = (Block) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.terra.activitystore.db.entity.Block[ id=" + id + " ]";
    }

}
