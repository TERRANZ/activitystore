package ru.terra.activitystore.db.manager;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;

public class CardPersistanceManager extends PersistanceManager<Card>
{

	@Override
	public Card findById(Integer id)
	{
		Criteria c = session.createCriteria(Card.class);
		c.add(Restrictions.eq("id", id));
		return (Card) c.uniqueResult();
	}

	public List<Card> getCardsFromBlock(Block block)
	{
		Criteria c = session.createCriteria(Card.class);
		c.add(Restrictions.eq("blockId", block.getId().getId()));
		return c.list();
	}
}
