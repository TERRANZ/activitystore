package ru.terra.activitystore.db.manager;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.terra.activitystore.db.entity.ListVal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Vlist;
import ru.terra.activitystore.db.manager.exceptions.IllegalOrphanException;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

/**
 *
 * @author terranz
 */
public class VlistJpaController implements Serializable
{

    public VlistJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Vlist vlist)
    {
        if (vlist.getListValList() == null)
        {
            vlist.setListValList(new ArrayList<ListVal>());
        }
        if (vlist.getCellList() == null)
        {
            vlist.setCellList(new ArrayList<Cell>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ListVal> attachedListValList = new ArrayList<ListVal>();
            for (ListVal listValListListValToAttach : vlist.getListValList())
            {
                listValListListValToAttach = em.getReference(listValListListValToAttach.getClass(), listValListListValToAttach.getValId());
                attachedListValList.add(listValListListValToAttach);
            }
            vlist.setListValList(attachedListValList);
            List<Cell> attachedCellList = new ArrayList<Cell>();
            for (Cell cellListCellToAttach : vlist.getCellList())
            {
                cellListCellToAttach = em.getReference(cellListCellToAttach.getClass(), cellListCellToAttach.getId());
                attachedCellList.add(cellListCellToAttach);
            }
            vlist.setCellList(attachedCellList);
            em.persist(vlist);
            for (ListVal listValListListVal : vlist.getListValList())
            {
                Vlist oldListIdOfListValListListVal = listValListListVal.getListId();
                listValListListVal.setListId(vlist);
                listValListListVal = em.merge(listValListListVal);
                if (oldListIdOfListValListListVal != null)
                {
                    oldListIdOfListValListListVal.getListValList().remove(listValListListVal);
                    oldListIdOfListValListListVal = em.merge(oldListIdOfListValListListVal);
                }
            }
            for (Cell cellListCell : vlist.getCellList())
            {
                Vlist oldListIdOfCellListCell = cellListCell.getListId();
                cellListCell.setListId(vlist);
                cellListCell = em.merge(cellListCell);
                if (oldListIdOfCellListCell != null)
                {
                    oldListIdOfCellListCell.getCellList().remove(cellListCell);
                    oldListIdOfCellListCell = em.merge(oldListIdOfCellListCell);
                }
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

    public void edit(Vlist vlist) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Vlist persistentVlist = em.find(Vlist.class, vlist.getId());
            List<ListVal> listValListOld = persistentVlist.getListValList();
            List<ListVal> listValListNew = vlist.getListValList();
            List<Cell> cellListOld = persistentVlist.getCellList();
            List<Cell> cellListNew = vlist.getCellList();
            List<String> illegalOrphanMessages = null;
            for (ListVal listValListOldListVal : listValListOld)
            {
                if (!listValListNew.contains(listValListOldListVal))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListVal " + listValListOldListVal + " since its listId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ListVal> attachedListValListNew = new ArrayList<ListVal>();
            for (ListVal listValListNewListValToAttach : listValListNew)
            {
                listValListNewListValToAttach = em.getReference(listValListNewListValToAttach.getClass(), listValListNewListValToAttach.getValId());
                attachedListValListNew.add(listValListNewListValToAttach);
            }
            listValListNew = attachedListValListNew;
            vlist.setListValList(listValListNew);
            List<Cell> attachedCellListNew = new ArrayList<Cell>();
            for (Cell cellListNewCellToAttach : cellListNew)
            {
                cellListNewCellToAttach = em.getReference(cellListNewCellToAttach.getClass(), cellListNewCellToAttach.getId());
                attachedCellListNew.add(cellListNewCellToAttach);
            }
            cellListNew = attachedCellListNew;
            vlist.setCellList(cellListNew);
            vlist = em.merge(vlist);
            for (ListVal listValListNewListVal : listValListNew)
            {
                if (!listValListOld.contains(listValListNewListVal))
                {
                    Vlist oldListIdOfListValListNewListVal = listValListNewListVal.getListId();
                    listValListNewListVal.setListId(vlist);
                    listValListNewListVal = em.merge(listValListNewListVal);
                    if (oldListIdOfListValListNewListVal != null && !oldListIdOfListValListNewListVal.equals(vlist))
                    {
                        oldListIdOfListValListNewListVal.getListValList().remove(listValListNewListVal);
                        oldListIdOfListValListNewListVal = em.merge(oldListIdOfListValListNewListVal);
                    }
                }
            }
            for (Cell cellListOldCell : cellListOld)
            {
                if (!cellListNew.contains(cellListOldCell))
                {
                    cellListOldCell.setListId(null);
                    cellListOldCell = em.merge(cellListOldCell);
                }
            }
            for (Cell cellListNewCell : cellListNew)
            {
                if (!cellListOld.contains(cellListNewCell))
                {
                    Vlist oldListIdOfCellListNewCell = cellListNewCell.getListId();
                    cellListNewCell.setListId(vlist);
                    cellListNewCell = em.merge(cellListNewCell);
                    if (oldListIdOfCellListNewCell != null && !oldListIdOfCellListNewCell.equals(vlist))
                    {
                        oldListIdOfCellListNewCell.getCellList().remove(cellListNewCell);
                        oldListIdOfCellListNewCell = em.merge(oldListIdOfCellListNewCell);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = vlist.getId();
                if (findVlist(id) == null)
                {
                    throw new NonexistentEntityException("The vlist with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Vlist vlist;
            try
            {
                vlist = em.getReference(Vlist.class, id);
                vlist.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The vlist with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ListVal> listValListOrphanCheck = vlist.getListValList();
            for (ListVal listValListOrphanCheckListVal : listValListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vlist (" + vlist + ") cannot be destroyed since the ListVal " + listValListOrphanCheckListVal + " in its listValList field has a non-nullable listId field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cell> cellList = vlist.getCellList();
            for (Cell cellListCell : cellList)
            {
                cellListCell.setListId(null);
                cellListCell = em.merge(cellListCell);
            }
            em.remove(vlist);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Vlist> findVlistEntities()
    {
        return findVlistEntities(true, -1, -1);
    }

    public List<Vlist> findVlistEntities(int maxResults, int firstResult)
    {
        return findVlistEntities(false, maxResults, firstResult);
    }

    private List<Vlist> findVlistEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vlist.class));
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

    public Vlist findVlist(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Vlist.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getVlistCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vlist> rt = cq.from(Vlist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
