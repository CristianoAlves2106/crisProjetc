/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.JPA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import persistencia.ConexaoBD;
import persistencia.PersistenciaException;
import vo.EnumSexo;
import vo.EnumUF;

/**
 *
 * @author colab
 */
public class AlunoDAO extends DAO<AlunoVO> {

    AlunoDAO alunoDAO;

    public AlunoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public int incluir(vo.AlunoVO alunoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            alunoDAO.inciarTransacao();
            alunoDAO.incluir(alunoVO);
            alunoDAO.confirmaTransacao();
            retorno = 1;
        } catch (Exception ex) {
            throw new PersistenciaException("Erro ao incluir novo aluno - " + ex.getMessage());
        }
        return retorno;
    }

    public int alterar(int alunoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            alunoDAO.inciarTransacao();
            alunoDAO.alterar(alunoVO);
            alunoDAO.confirmaTransacao();
            retorno = 1;
        } catch (Exception ex) {

            retorno = 0;
            throw new PersistenciaException("Erro ao alterar o aluno - " + ex.getMessage());
        }
        return retorno;
    }

    public int excluir(int matricula) throws PersistenciaException {
        entityManager.getTransaction().begin();
        AlunoVO grupoVo = null;
        Query query = entityManager.createQuery("SELECT g FROM GrupoProdutoVo g WHERE TRIM(g.nome) =  'Enlatados'", AlunoVO.class);

        grupoVo = (AlunoVO) query.getResultList().get(0);
        entityManager.remove(grupoVo);
        entityManager.getTransaction().commit();

        return 0;
    }

    public AlunoVO buscarPorMatricula(int matricula) throws PersistenciaException {

        try {
            return this.entityManager.find(AlunoVO.class, matricula);

        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por matricula - " + ex.getMessage());
        }

    }

    public List<AlunoVO> buscarPorNome(String nome) throws PersistenciaException {

        try {
            Query comandoSQL = this.entityManager.createQuery("SELECT * FROM AlunoVO WHERE UPPER(NOME) LIKE '" + nome.trim().toUpperCase() + "%' ORDER BY NOME");
            return comandoSQL.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na selecaoo por nome - " + ex.getMessage());
        }

    }
}
