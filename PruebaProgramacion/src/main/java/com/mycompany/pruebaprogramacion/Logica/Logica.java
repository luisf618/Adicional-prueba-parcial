/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebaprogramacion.Logica;

import com.mycompany.pruebaprogramacion.Clases.Celular;
import com.mycompany.pruebaprogramacion.Clases.Cliente;
import com.mycompany.pruebaprogramacion.Clases.Recargas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author utpl
 */
public class Logica {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_PruebaProgramacion_jar_1.0-SNAPSHOTPU");
    private EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    public Cliente buscarPorId(String id) {
        return em.find(Cliente.class, id);  // Búsqueda por clave primaria
    }

    public Cliente buscarPorCedula(String cedula) {
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.cedula = :cedula", Cliente.class);
        query.setParameter("cedula", cedula);

        List<Cliente> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public Celular buscarPorNumero(String numero) {
        TypedQuery<Celular> query = em.createQuery(
                "SELECT c FROM Celular c WHERE c.numero = :numero", Celular.class);
        query.setParameter("numero", numero);

        List<Celular> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public void cerrar() {
        em.close();
        emf.close();
    }

    public void actualizarCelular(Celular c, Recargas r) {

        try {
            tx.begin();

            // Buscar al cliente
            Recargas rec = em.find(Recargas.class, r.getIdReca());
            Celular cel = em.find(Celular.class, c.getIdCel());
            if (cel != null && rec != null) {
                // Modificar el nombre
                cel.setSaldo(c.getSaldo() + rec.getSaldo());
                cel.setMegas(c.getMegas() + rec.getMegas());

                // Guardar cambios
                em.merge(cel);
                System.out.println("Cliente actualizado correctamente.");
            } else {
                System.out.println("Cliente no encontrado.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

}
