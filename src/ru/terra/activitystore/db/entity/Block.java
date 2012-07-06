package ru.terra.activitystore.db.entity;

// Generated 05.07.2012 13:20:05 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Block generated by hbm2java
 */
@Entity
@Table(name = "block", catalog = "activitystore")
public class Block implements java.io.Serializable
{

	private BlockId id;
	private Block block;
	private String name;
	private Date creationDate;
	private Date updateDate;
	private Set<Block> blocks = new HashSet<Block>(0);

	public Block()
	{
	}

	public Block(BlockId id, Block block, String name, Date creationDate)
	{
		this.id = id;
		this.block = block;
		this.name = name;
		this.creationDate = creationDate;
	}

	public Block(BlockId id, Block block, String name, Date creationDate, Date updateDate, Set<Block> blocks)
	{
		this.id = id;
		this.block = block;
		this.name = name;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.blocks = blocks;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
			@AttributeOverride(name = "parent", column = @Column(name = "parent", nullable = false)) })
	public BlockId getId()
	{
		return this.id;
	}

	public void setId(BlockId id)
	{
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent", nullable = false, insertable = false, updatable = false)
	public Block getBlock()
	{
		return this.block;
	}

	public void setBlock(Block block)
	{
		this.block = block;
	}

	@Column(name = "name", nullable = false, length = 200)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate()
	{
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "block")
	public Set<Block> getBlocks()
	{
		return this.blocks;
	}

	public void setBlocks(Set<Block> blocks)
	{
		this.blocks = blocks;
	}

}
