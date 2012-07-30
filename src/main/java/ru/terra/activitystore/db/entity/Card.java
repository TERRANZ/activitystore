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
@Table(name = "card", catalog = "activitystore", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c"),
    @NamedQuery(name = "Card.findById", query = "SELECT c FROM Card c WHERE c.id = :id"),
    @NamedQuery(name = "Card.findByBlockId", query = "SELECT c FROM Card c WHERE c.blockId = :blockId"),
    @NamedQuery(name = "Card.findByTemplateId", query = "SELECT c FROM Card c WHERE c.templateId = :templateId"),
    @NamedQuery(name = "Card.findByName", query = "SELECT c FROM Card c WHERE c.name = :name"),
    @NamedQuery(name = "Card.findByCreationDate", query = "SELECT c FROM Card c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "Card.findByUpdateDate", query = "SELECT c FROM Card c WHERE c.updateDate = :updateDate")
})
public class Card implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "block_id")
    private Integer blockId;
    @Column(name = "template_id")
    private Integer templateId;
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
    @JoinTable(name = "card_cell", joinColumns =
    {
        @JoinColumn(name = "card_id", referencedColumnName = "id", nullable = false)
    }, inverseJoinColumns =
    {
        @JoinColumn(name = "cell_id", referencedColumnName = "id", nullable = false)
    })
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Cell> cellList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card", fetch = FetchType.LAZY)
    private List<Template> templateList;

    public Card()
    {
    }

    public Card(Integer id)
    {
        this.id = id;
    }

    public Card(Integer id, String name, Date creationDate)
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

    public Integer getBlockId()
    {
        return blockId;
    }

    public void setBlockId(Integer blockId)
    {
        this.blockId = blockId;
    }

    public Integer getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(Integer templateId)
    {
        this.templateId = templateId;
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

    @XmlTransient
    public List<Cell> getCellList()
    {
        return cellList;
    }

    public void setCellList(List<Cell> cellList)
    {
        this.cellList = cellList;
    }

    @XmlTransient
    public List<Template> getTemplateList()
    {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList)
    {
        this.templateList = templateList;
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
        if (!(object instanceof Card))
        {
            return false;
        }
        Card other = (Card) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ru.terra.activitystore.db.entity.Card[ id=" + id + " ]";
    }
    
}
