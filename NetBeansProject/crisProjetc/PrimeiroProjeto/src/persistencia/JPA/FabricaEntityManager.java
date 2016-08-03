/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PessimisticLockException;
import persistencia.PersistenciaException;

/**
 *
 * @author colab
 */
public class FabricaEntityManager {

    private static EntityManagerFactory entityManagerFactory = null;

    public FabricaEntityManager() {
    }

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("UESTOQUE");
    }

    public static EntityManager getEntityManager() throws PersistenciaException {
        
        if (entityManagerFactory ==null) {
            throw new PessimisticLockException("Unidade de Persistencia não iniciada");
        }
        return entityManagerFactory.createEntityManager();
    }
}