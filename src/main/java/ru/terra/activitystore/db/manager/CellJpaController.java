package ru.terra.activitystore.db.manager;

import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Vlist;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author terranz
 */
public class CellJpaController implements Serializable {

    public CellJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cell cell) {
        if (cell.getCreationDate() == null)
            cell.setCreationDate(new Date());
        if (cell.getUpdateDate() == null)
            cell.setUpdateDate(new Date());
        if (cell.getCardList() == null) {
            cell.setCardList(new ArrayList<Card>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vlist listId = cell.getListId();
            if (listId != null) {
                listId = em.getReference(listId.getClass(), listId.getId());
                cell.setListId(listId);
            }
            List<Card> attachedCardList = new ArrayList<Card>();
            for (Card cardListCardToAttach : cell.getCardList()) {
                cardListCardToAttach = em.getReference(cardListCardToAttach.getClass(), cardListCardToAttach.getId());
                attachedCardList.add(cardListCardToAttach);
            }
            cell.setCardList(attachedCardList);
            em.persist(cell);
            if (listId != null) {
                listId.getCellList().add(cell);
                listId = em.merge(listId);
            }
            for (Card cardListCard : cell.getCardList()) {
                cardListCard.getCellList().add(cell);
                cardListCard = em.merge(cardListCard);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cell cell) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cell persistentCell = em.find(Cell.class, cell.getId());
            Vlist listIdOld = persistentCell.getListId();
            Vlist listIdNew = cell.getListId();
            List<Card> cardListOld = persistentCell.getCardList();
            List<Card> cardListNew = cell.getCardList();
            if (listIdNew != null) {
                listIdNew = em.getReference(listIdNew.getClass(), listIdNew.getId());
                cell.setListId(listIdNew);
            }
            List<Card> attachedCardListNew = new ArrayList<Card>();
            for (Card cardListNewCardToAttach : cardListNew) {
                cardListNewCardToAttach = em.getReference(cardListNewCardToAttach.getClass(), cardListNewCardToAttach.getId());
                attachedCardListNew.add(cardListNewCardToAttach);
            }
            cardListNew = attachedCardListNew;
            cell.setCardList(cardListNew);
            cell = em.merge(cell);
            if (listIdOld != null && !listIdOld.equals(listIdNew)) {
                listIdOld.getCellList().remove(cell);
                listIdOld = em.merge(listIdOld);
            }
            if (listIdNew != null && !listIdNew.equals(listIdOld)) {
                listIdNew.getCellList().add(cell);
                listIdNew = em.merge(listIdNew);
            }
            for (Card cardListOldCard : cardListOld) {
                if (!cardListNew.contains(cardListOldCard)) {
                    cardListOldCard.getCellList().remove(cell);
                    cardListOldCard = em.merge(cardListOldCard);
                }
            }
            for (Card cardListNewCard : cardListNew) {
                if (!cardListOld.contains(cardListNewCard)) {
                    cardListNewCard.getCellList().add(cell);
                    cardListNewCard = em.merge(cardListNewCard);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cell.getId();
                if (findCell(id) == null) {
                    throw new NonexistentEntityException("The cell with id " + id + " no longer exists.");
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
            Cell cell;
            try {
                cell = em.getReference(Cell.class, id);
                cell.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cell with id " + id + " no longer exists.", enfe);
            }
            Vlist listId = cell.getListId();
            if (listId != null) {
                listId.getCellList().remove(cell);
                listId = em.merge(listId);
            }
            List<Card> cardList = cell.getCardList();
            for (Card cardListCard : cardList) {
                cardListCard.getCellList().remove(cell);
                cardListCard = em.merge(cardListCard);
            }
            em.remove(cell);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cell> findCellEntities() {
        return findCellEntities(true, -1, -1);
    }

    public List<Cell> findCellEntities(int maxResults, int firstResult) {
        return findCellEntities(false, maxResults, firstResult);
    }

    private List<Cell> findCellEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cell.class));
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

    public Cell findCell(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cell.class, id);
        } finally {
            em.close();
        }
    }

    public int getCellCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cell> rt = cq.from(Cell.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Cell> findCellEntities(Card card) {
        return card.getCellList();
    }

}
