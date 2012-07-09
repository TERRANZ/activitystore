package ru.terra.activitystore.db.manager;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;

public class CellPersistanceManager extends PersistanceManager<Cell>
{

	@Override
	public Cell findById(Integer id)
	{
		Criteria c = session.createCriteria(Cell.class);
		c.add(Restrictions.eq("id", id));
		return (Cell) c.uniqueResult();
	}

	public List<Cell> getCellsFromCard(Card card)
	{
		Criteria c = session.createCriteria(Cell.class);
		c.add(Restrictions.eq("card", card));
		return c.list();
	}
}
