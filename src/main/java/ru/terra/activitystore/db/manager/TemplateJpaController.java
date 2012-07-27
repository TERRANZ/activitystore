package ru.terra.activitystore.db.manager;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

/**
 *
 * @author terranz
 */
public class TemplateJpaController implements Serializable
{

    public TemplateJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Template template)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Card card = template.getCard();
            if (card != null)
            {
                card = em.getReference(card.getClass(), card.getId());
                template.setCard(card);
            }
            em.persist(template);
            if (card != null)
            {
                card.getTemplateList().add(template);
                card = em.merge(card);
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

    public void edit(Template template) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Template persistentTemplate = em.find(Template.class, template.getId());
            Card cardOld = persistentTemplate.getCard();
            Card cardNew = template.getCard();
            if (cardNew != null)
            {
                cardNew = em.getReference(cardNew.getClass(), cardNew.getId());
                template.setCard(cardNew);
            }
            template = em.merge(template);
            if (cardOld != null && !cardOld.equals(cardNew))
            {
                cardOld.getTemplateList().remove(template);
                cardOld = em.merge(cardOld);
            }
            if (cardNew != null && !cardNew.equals(cardOld))
            {
                cardNew.getTemplateList().add(template);
                cardNew = em.merge(cardNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = template.getId();
                if (findTemplate(id) == null)
                {
                    throw new NonexistentEntityException("The template with id " + id + " no longer exists.");
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
            Template template;
            try
            {
                template = em.getReference(Template.class, id);
                template.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The template with id " + id + " no longer exists.", enfe);
            }
            Card card = template.getCard();
            if (card != null)
            {
                card.getTemplateList().remove(template);
                card = em.merge(card);
            }
            em.remove(template);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Template> findTemplateEntities()
    {
        return findTemplateEntities(true, -1, -1);
    }

    public List<Template> findTemplateEntities(int maxResults, int firstResult)
    {
        return findTemplateEntities(false, maxResults, firstResult);
    }

    private List<Template> findTemplateEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Template.class));
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

    public Template findTemplate(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Template.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getTemplateCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Template> rt = cq.from(Template.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
