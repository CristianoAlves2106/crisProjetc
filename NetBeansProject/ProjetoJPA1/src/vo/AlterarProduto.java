/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author colab
 */
public class AlterarProduto {

    public static void main(String[] args) {
        EntityManagerFactory fabricaEntityManager;
        EntityManager entityManager = null;
        try {
            fabricaEntityManager = Persistence.createEntityManagerFactory("UESTOQUE");

            entityManager = fabricaEntityManager.createEntityManager();

            entityManager.getTransaction().begin();

            ProdutoVO produtoVO = new ProdutoVO();

            Query query = entityManager.createQuery("SELECT p FROM ProdutoVO p WHERE TRIM(p.nome) = 'Ervilha'", ProdutoVO.class);

            produtoVO = (ProdutoVO) query.getResultList().get(0);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
        }
    }
}
