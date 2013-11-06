/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author terranz
 */
@Entity
@Table(name = "list_val", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "ListVal.findAll", query = "SELECT l FROM ListVal l"),
        @NamedQuery(name = "ListVal.findByValId", query = "SELECT l FROM ListVal l WHERE l.valId = :valId"),
        @NamedQuery(name = "ListVal.findByValue", query = "SELECT l FROM ListVal l WHERE l.value = :value")})
public class ListVal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "val_id", nullable = false)
    private Integer valId;
    @Basic(optional = false)
    @Column(name = "value", nullable = false, length = 500)
    private String value;
    @JoinColumn(name = "list_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Vlist listId;

    public ListVal() {
    }

    public ListVal(Integer valId) {
        this.valId = valId;
    }

    public ListVal(Integer valId, String value) {
        this.valId = valId;
        this.value = value;
    }

    public Integer getValId() {
        return valId;
    }

    public void setValId(Integer valId) {
        this.valId = valId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Vlist getListId() {
        return listId;
    }

    public void setListId(Vlist listId) {
        this.listId = listId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valId != null ? valId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof ListVal)) {
            return false;
        }
        ListVal other = (ListVal) object;
        if ((this.valId == null && other.valId != null) || (this.valId != null && !this.valId.equals(other.valId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.terra.activitystore.db.entity.ListVal[ valId=" + valId + " ]";
    }

}
