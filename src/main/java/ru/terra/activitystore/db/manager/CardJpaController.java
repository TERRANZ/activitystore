package ru.terra.activitystore.db.manager;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.db.manager.exceptions.IllegalOrphanException;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author terranz
 */
public class CardJpaController implements Serializable {

    public CardJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Card card) {
        if (card.getCreationDate() == null)
            card.setCreationDate(new Date());
        if (card.getUpdateDate() == null)
            card.setUpdateDate(new Date());
        if (card.getCellList() == null) {
            card.setCellList(new ArrayList<Cell>());
        }
        if (card.getTemplateList() == null) {
            card.setTemplateList(new ArrayList<Template>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cell> attachedCellList = new ArrayList<Cell>();
            for (Cell cellListCellToAttach : card.getCellList()) {
                cellListCellToAttach = em.getReference(cellListCellToAttach.getClass(), cellListCellToAttach.getId());
                attachedCellList.add(cellListCellToAttach);
            }
            card.setCellList(attachedCellList);
            List<Template> attachedTemplateList = new ArrayList<Template>();
            for (Template templateListTemplateToAttach : card.getTemplateList()) {
                templateListTemplateToAttach = em.getReference(templateListTemplateToAttach.getClass(), templateListTemplateToAttach.getId());
                attachedTemplateList.add(templateListTemplateToAttach);
            }
            card.setTemplateList(attachedTemplateList);
            em.persist(card);
            for (Cell cellListCell : card.getCellList()) {
                cellListCell.getCardList().add(card);
                cellListCell = em.merge(cellListCell);
            }
            for (Template templateListTemplate : card.getTemplateList()) {
                Card oldCardOfTemplateListTemplate = templateListTemplate.getCard();
                templateListTemplate.setCard(card);
                templateListTemplate = em.merge(templateListTemplate);
                if (oldCardOfTemplateListTemplate != null) {
                    oldCardOfTemplateListTemplate.getTemplateList().remove(templateListTemplate);
                    oldCardOfTemplateListTemplate = em.merge(oldCardOfTemplateListTemplate);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Card card) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Card persistentCard = em.find(Card.class, card.getId());
            List<Cell> cellListOld = persistentCard.getCellList();
            List<Cell> cellListNew = card.getCellList();
            List<Template> templateListOld = persistentCard.getTemplateList();
            List<Template> templateListNew = card.getTemplateList();
            List<String> illegalOrphanMessages = null;
            for (Template templateListOldTemplate : templateListOld) {
                if (!templateListNew.contains(templateListOldTemplate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Template " + templateListOldTemplate + " since its card field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cell> attachedCellListNew = new ArrayList<Cell>();
            for (Cell cellListNewCellToAttach : cellListNew) {
                cellListNewCellToAttach = em.getReference(cellListNewCellToAttach.getClass(), cellListNewCellToAttach.getId());
                attachedCellListNew.add(cellListNewCellToAttach);
            }
            cellListNew = attachedCellListNew;
            card.setCellList(cellListNew);
            List<Template> attachedTemplateListNew = new ArrayList<Template>();
            for (Template templateListNewTemplateToAttach : templateListNew) {
                templateListNewTemplateToAttach = em
                        .getReference(templateListNewTemplateToAttach.getClass(), templateListNewTemplateToAttach.getId());
                attachedTemplateListNew.add(templateListNewTemplateToAttach);
            }
            templateListNew = attachedTemplateListNew;
            card.setTemplateList(templateListNew);
            card = em.merge(card);
            for (Cell cellListOldCell : cellListOld) {
                if (!cellListNew.contains(cellListOldCell)) {
                    cellListOldCell.getCardList().remove(card);
                    cellListOldCell = em.merge(cellListOldCell);
                }
            }
            for (Cell cellListNewCell : cellListNew) {
                if (!cellListOld.contains(cellListNewCell)) {
                    cellListNewCell.getCardList().add(card);
                    cellListNewCell = em.merge(cellListNewCell);
                }
            }
            for (Template templateListNewTemplate : templateListNew) {
                if (!templateListOld.contains(templateListNewTemplate)) {
                    Card oldCardOfTemplateListNewTemplate = templateListNewTemplate.getCard();
                    templateListNewTemplate.setCard(card);
                    templateListNewTemplate = em.merge(templateListNewTemplate);
                    if (oldCardOfTemplateListNewTemplate != null && !oldCardOfTemplateListNewTemplate.equals(card)) {
                        oldCardOfTemplateListNewTemplate.getTemplateList().remove(templateListNewTemplate);
                        oldCardOfTemplateListNewTemplate = em.merge(oldCardOfTemplateListNewTemplate);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = card.getId();
                if (findCard(id) == null) {
                    throw new NonexistentEntityException("The card with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Card card;
            try {
                card = em.getReference(Card.class, id);
                card.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The card with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Template> templateListOrphanCheck = card.getTemplateList();
            for (Template templateListOrphanCheckTemplate : templateListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Card (" + card + ") cannot be destroyed since the Template " + templateListOrphanCheckTemplate
                        + " in its templateList field has a non-nullable card field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cell> cellList = card.getCellList();
            for (Cell cellListCell : cellList) {
                cellListCell.getCardList().remove(card);
                cellListCell = em.merge(cellListCell);
            }
            em.remove(card);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Card> findCardEntities() {
        return findCardEntities(true, -1, -1);
    }

    public List<Card> findCardEntities(int maxResults, int firstResult) {
        return findCardEntities(false, maxResults, firstResult);
    }

    public List<Card> findCardEntities(Block block) {
        EntityManager em = getEntityManager();
        try {
            Query cards = em.createNamedQuery("Card.findByBlockId").setParameter("blockId", block.getId());
            return cards.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Card> findCardEntities(Template block) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            cq.select(cq.from(Card.class));
            ParameterExpression<Integer> p = cb.parameter(Integer.class);
            Root<Card> c = cq.from(Card.class);
            cq.where(cb.equal(c.get("templateId"), p));
            TypedQuery<Card> q = em.createQuery(cq);
            q.setParameter(p, block.getId());
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    private List<Card> findCardEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Card.class));
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

    public Card findCard(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Card.class, id);
        } finally {
            em.close();
        }
    }

    public int getCardCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Card> rt = cq.from(Card.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
