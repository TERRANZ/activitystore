/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.activitystore.db.manager;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.activitystore.db.entity.CardCellVal;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class CardCellValJpaController implements Serializable {

	public CardCellValJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(CardCellVal cardCellVal) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(cardCellVal);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(CardCellVal cardCellVal) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			cardCellVal = em.merge(cardCellVal);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = cardCellVal.getId();
				if (findCardCellVal(id) == null) {
					throw new NonexistentEntityException("The cardCellVal with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			CardCellVal cardCellVal;
			try {
				cardCellVal = em.getReference(CardCellVal.class, id);
				cardCellVal.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The cardCellVal with id " + id + " no longer exists.", enfe);
			}
			em.remove(cardCellVal);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<CardCellVal> findCardCellValEntities() {
		return findCardCellValEntities(true, -1, -1);
	}

	public List<CardCellVal> findCardCellValEntities(int maxResults, int firstResult) {
		return findCardCellValEntities(false, maxResults, firstResult);
	}

	public String getVal(Integer cardId, Integer cellId) {
		CardCellVal ccv = getCardCellVal(cardId, cellId);
		return ccv == null ? "" : ccv.getVal();
	}

	public CardCellVal getCardCellVal(Integer cardId, Integer cellId) {
		EntityManager em = getEntityManager();
		Query q = em.createNamedQuery("CardCellVal.findByCardIdAndCellId").setParameter("cardId", cardId).setParameter("cellId", cellId);
		try {
			return (CardCellVal) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	private List<CardCellVal> findCardCellValEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(CardCellVal.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public CardCellVal findCardCellVal(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(CardCellVal.class, id);
		} finally {
			em.close();
		}
	}

	public int getCardCellValCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<CardCellVal> rt = cq.from(CardCellVal.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
