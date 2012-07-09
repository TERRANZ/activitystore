package ru.terra.activitystore.db.manager;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ru.terra.activitystore.db.entity.Block;

public class BlockPersistanceManager extends PersistanceManager<Block>
{
	@Override
	public Block findById(Integer id)
	{
		Criteria c = session.createCriteria(Block.class);
		c.add(Restrictions.eq("id", id));
		return (Block) c.uniqueResult();
	}

	public List<Block> getChildrenBlocks(Block root)
	{
		Criteria c = session.createCriteria(Block.class);
		c.add(Restrictions.eq("parent", root.getId().getId()));
		return c.list();
	}
}
