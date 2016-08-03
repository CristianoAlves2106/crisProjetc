/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.persistence.EntityManager;

/**
 *
 * @author colab
 */
public class DAO<VO> {

    protected EntityManager entityManager;

    public DAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void incluir(VO vo) throws PersistenciaException {
        this.entityManager.persist(vo);
    }

    public void alterar(VO vo) throws PersistenciaException {
        this.entityManager.persist(vo);
    }

    public void excluir(VO vo) throws PersistenciaException {
        this.entityManager.remove(vo);
    }

    public void inciarTransacao() {
        this.entityManager.getTransaction().begin();
    }
    public void cancelarTransacao() {
        this.entityManager.getTransaction().rollback();
    }
    public void confirmaTransacao() {
        this.entityManager.getTransaction().commit();
    }
}
