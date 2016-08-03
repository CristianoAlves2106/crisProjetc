/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author colab
 */
public class ConsultarProduto {

    public static void main(String[] args) {
        EntityManagerFactory fabricaEntityManager = null;
        EntityManager entityManager = null;
        try {
            fabricaEntityManager = Persistence.createEntityManagerFactory("UESTOQUE");

            entityManager = fabricaEntityManager.createEntityManager();

            entityManager.getTransaction().begin();
            List<ProdutoVO> listaProdutoVO = null;
            Query query = entityManager.createQuery("SELECT p FROM ProdutoVO p ORDER BY p.nome", ProdutoVO.class);

            listaProdutoVO = query.getResultList();
            entityManager.getTransaction().commit();

            for (ProdutoVO produtoVO : listaProdutoVO) {
                System.out.println("-----------Produto-----------");

                System.out.println("Codigo :" + produtoVO.getCodigo());

                System.out.println("Nome :" + produtoVO.getNome());

                System.out.println("Estoque :" + produtoVO.getEstoque());

                System.out.println("-----------Grupo Produto-----------");
                
                System.out.println("\tCodigo :" + produtoVO.getGrupo().getCodigo());
                
                System.out.println("\tNome :"+produtoVO.getGrupo().getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
}
