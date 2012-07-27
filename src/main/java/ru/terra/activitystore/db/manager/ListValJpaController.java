package ru.terra.activitystore.db.manager;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.terra.activitystore.db.entity.ListVal;
import ru.terra.activitystore.db.entity.Vlist;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

/**
 *
 * @author terranz
 */
public class ListValJpaController implements Serializable
{

    public ListValJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(ListVal listVal)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Vlist listId = listVal.getListId();
            if (listId != null)
            {
                listId = em.getReference(listId.getClass(), listId.getId());
                listVal.setListId(listId);
            }
            em.persist(listVal);
            if (listId != null)
            {
                listId.getListValList().add(listVal);
                listId = em.merge(listId);
            }
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(ListVal listVal) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            ListVal persistentListVal = em.find(ListVal.class, listVal.getValId());
            Vlist listIdOld = persistentListVal.getListId();
            Vlist listIdNew = listVal.getListId();
            if (listIdNew != null)
            {
                listIdNew = em.getReference(listIdNew.getClass(), listIdNew.getId());
                listVal.setListId(listIdNew);
            }
            listVal = em.merge(listVal);
            if (listIdOld != null && !listIdOld.equals(listIdNew))
            {
                listIdOld.getListValList().remove(listVal);
                listIdOld = em.merge(listIdOld);
            }
            if (listIdNew != null && !listIdNew.equals(listIdOld))
            {
                listIdNew.getListValList().add(listVal);
                listIdNew = em.merge(listIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = listVal.getValId();
                if (findListVal(id) == null)
                {
                    throw new NonexistentEntityException("The listVal with id " + id + " no longer exists.");
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
            ListVal listVal;
            try
            {
                listVal = em.getReference(ListVal.class, id);
                listVal.getValId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The listVal with id " + id + " no longer exists.", enfe);
            }
            Vlist listId = listVal.getListId();
            if (listId != null)
            {
                listId.getListValList().remove(listVal);
                listId = em.merge(listId);
            }
            em.remove(listVal);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<ListVal> findListValEntities()
    {
        return findListValEntities(true, -1, -1);
    }

    public List<ListVal> findListValEntities(int maxResults, int firstResult)
    {
        return findListValEntities(false, maxResults, firstResult);
    }

    private List<ListVal> findListValEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListVal.class));
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

    public ListVal findListVal(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(ListVal.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getListValCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListVal> rt = cq.from(ListVal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
