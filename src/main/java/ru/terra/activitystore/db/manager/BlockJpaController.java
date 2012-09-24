package ru.terra.activitystore.db.manager;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class BlockJpaController implements Serializable
{

	public BlockJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Block block)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(block);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Block block) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			block = em.merge(block);
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = block.getId();
				if (findBlock(id) == null)
				{
					throw new NonexistentEntityException("The block with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Block block;
			try
			{
				block = em.getReference(Block.class, id);
				block.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The block with id " + id + " no longer exists.", enfe);
			}
			em.remove(block);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Block> findBlockEntities()
	{
		return findBlockEntities(true, -1, -1);
	}

	public List<Block> findBlockEntities(int maxResults, int firstResult)
	{
		return findBlockEntities(false, maxResults, firstResult);
	}

	private List<Block> findBlockEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Block.class));
			Query q = em.createQuery(cq);
			if (!all)
			{
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally
		{
			em.close();
		}
	}

	public List<Block> findBlockEntities(Block root)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query blocks = em.createNamedQuery("Block.findByParent").setParameter("parent", root.getId());
			return blocks.getResultList();
		} finally
		{
			em.close();
		}
	}

	public Block findBlock(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Block.class, id);
		} finally
		{
			em.close();
		}
	}

	public int getBlockCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Block> rt = cq.from(Block.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

}
