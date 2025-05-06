/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebaprogramacion.Persistencia;

import com.mycompany.pruebaprogramacion.Clases.Celular;
import com.mycompany.pruebaprogramacion.Persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author utpl
 */
public class CelularJpaController implements Serializable {

    public CelularJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_PruebaProgramacion_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Celular celular) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(celular);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Celular celular) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            celular = em.merge(celular);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = celular.getIdCel();
                if (findCelular(id) == null) {
                    throw new NonexistentEntityException("The celular with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Celular celular;
            try {
                celular = em.getReference(Celular.class, id);
                celular.getIdCel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The celular with id " + id + " no longer exists.", enfe);
            }
            em.remove(celular);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Celular> findCelularEntities() {
        return findCelularEntities(true, -1, -1);
    }

    public List<Celular> findCelularEntities(int maxResults, int firstResult) {
        return findCelularEntities(false, maxResults, firstResult);
    }

    private List<Celular> findCelularEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Celular.class));
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

    public Celular findCelular(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Celular.class, id);
        } finally {
            em.close();
        }
    }

    public int getCelularCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Celular> rt = cq.from(Celular.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
