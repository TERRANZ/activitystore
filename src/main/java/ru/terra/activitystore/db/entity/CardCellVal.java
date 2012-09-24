/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author terranz
 */
@Entity
@Table(name = "card_cell_val", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "CardCellVal.findAll", query = "SELECT c FROM CardCellVal c"),
    @NamedQuery(name = "CardCellVal.findById", query = "SELECT c FROM CardCellVal c WHERE c.id = :id"),
    @NamedQuery(name = "CardCellVal.findByCellId", query = "SELECT c FROM CardCellVal c WHERE c.cellId = :cellId"),
    @NamedQuery(name = "CardCellVal.findByCardId", query = "SELECT c FROM CardCellVal c WHERE c.cardId = :cardId"),
    @NamedQuery(name = "CardCellVal.findByCardIdAndCellId", query = "SELECT c FROM CardCellVal c WHERE c.cardId = :cardId AND c.cellId = :cellId"),
    @NamedQuery(name = "CardCellVal.findByVal", query = "SELECT c FROM CardCellVal c WHERE c.val = :val")
})
public class CardCellVal implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cell_id", nullable = false)
    private int cellId;
    @Basic(optional = false)
    @Column(name = "card_id", nullable = false)
    private int cardId;
    @Column(name = "val", length = 512)
    private String val;

    public CardCellVal()
    {
    }

    public CardCellVal(Integer id)
    {
        this.id = id;
    }

    public CardCellVal(Integer id, int cellId, int cardId)
    {
        this.id = id;
        this.cellId = cellId;
        this.cardId = cardId;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getCellId()
    {
        return cellId;
    }

    public void setCellId(int cellId)
    {
        this.cellId = cellId;
    }

    public int getCardId()
    {
        return cardId;
    }

    public void setCardId(int cardId)
    {
        this.cardId = cardId;
    }

    public String getVal()
    {
        return val;
    }

    public void setVal(String val)
    {
        this.val = val;
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
        if (!(object instanceof CardCellVal))
        {
            return false;
        }
        CardCellVal other = (CardCellVal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ru.terra.activitystore.db.entity.CardCellVal[ id=" + id + " ]";
    }
    
}
