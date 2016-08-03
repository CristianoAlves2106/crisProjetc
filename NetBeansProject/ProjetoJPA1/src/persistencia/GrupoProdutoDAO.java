/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import vo.GrupoProdutoVo;

/**
 *
 * @author colab
 */
public class GrupoProdutoDAO extends DAO<GrupoProdutoVo> {

    public GrupoProdutoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public GrupoProdutoVo buscarPorCodigo(int codigo) throws PersistenciaException {
        return this.entityManager.find(GrupoProdutoVo.class, codigo);
    }

    public List<GrupoProdutoVo> buscarTodos() throws PersistenciaException {
        try {
            Query query = this.entityManager.createQuery("SELECT * FROM GrupoProdutoVo ORDER BY nome");
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Não foi possivel obter a lista de Grupos de Produtos");
        }
    }

}
