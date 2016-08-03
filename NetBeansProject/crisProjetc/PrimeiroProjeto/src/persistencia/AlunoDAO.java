package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vo.AlunoVO;
import vo.EnumSexo;
import vo.EnumUF;

public class AlunoDAO extends DAO {
    
    public AlunoDAO(ConexaoBD conexao) throws PersistenciaException {
        super(conexao);
    }
    
    

    public int incluir(AlunoVO alunoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            PreparedStatement comando = conexao.getConexao().prepareStatement(
                    "INSERT INTO Aluno ( nome, nomemae, nomepai, sexo, " +
                    "logradouro, numero, bairro, cidade, uf )VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?)");
            comando.setString(1, alunoVO.getNome());
            comando.setString(2, alunoVO.getNomeMae());
            comando.setString(3, alunoVO.getNomePai());
            comando.setInt(4, alunoVO.getSexo().ordinal());
            comando.setString(5, alunoVO.getEndereco().getLogradouro());
            comando.setInt(6, alunoVO.getEndereco().getNumero());
            comando.setString(7, alunoVO.getEndereco().getBairro());
            comando.setString(8, alunoVO.getEndereco().getCidade());
            comando.setString(9, alunoVO.getEndereco().getUf().name());
            retorno = comando.executeUpdate();
            comando.close();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir novo aluno - "+ex.getMessage());
        }
        return retorno;
    }

    public int alterar(AlunoVO alunoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            PreparedStatement comando = conexao.getConexao().prepareStatement(
                    "UPDATE Aluno SET nome=?, nomemae=?, nomepai=?, sexo=?," +
                    "logradouro=?, numero=?, bairro=?, cidade=?, uf=? WHERE matricula=?");
            comando.setString(1, alunoVO.getNome());
            comando.setString(2, alunoVO.getNomeMae());
            comando.setString(3, alunoVO.getNomePai());
            comando.setInt(4, alunoVO.getSexo().ordinal());
            comando.setString(5, alunoVO.getEndereco().getLogradouro());
            comando.setInt(6, alunoVO.getEndereco().getNumero());
            comando.setString(7, alunoVO.getEndereco().getBairro());
            comando.setString(8, alunoVO.getEndereco().getCidade());
            comando.setString(9, alunoVO.getEndereco().getUf().name());
            comando.setInt(10, alunoVO.getMatricula());
            
            retorno = comando.executeUpdate();
            comando.close();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao alterar o aluno - "+ex.getMessage());
        }
        return retorno;
    }

    public int excluir(int matricula) throws PersistenciaException {
        int retorno = 0;
        try {
            PreparedStatement comando = conexao.getConexao().prepareStatement("DELETE FROM Aluno WHERE matricula=?");
            comando.setInt(1, matricula);
            retorno = comando.executeUpdate();
            comando.close();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir o aluno - "+ex.getMessage());
        }
        return retorno;
    }

    public AlunoVO buscarPorMatricula(int matricula) throws PersistenciaException {

        AlunoVO alu = null;

        try {
            PreparedStatement comando = conexao.getConexao().prepareStatement("SELECT * FROM Aluno WHERE matricula = ?");
            comando.setInt(1, matricula);
            ResultSet rs = comando.executeQuery();
            if (rs.next()) {
                alu = new AlunoVO();
                alu.setMatricula(rs.getInt("matricula"));
                alu.setNome(rs.getString("Nome").trim());
                alu.setNomeMae(rs.getString("nomemae"));
                alu.setNomePai(rs.getString("nomepai"));
                alu.setSexo(EnumSexo.values()[rs.getInt("sexo")]);
                alu.getEndereco().setLogradouro(rs.getString("logradouro"));
                alu.getEndereco().setNumero(rs.getInt("numero"));
                alu.getEndereco().setBairro(rs.getString("bairro"));
                alu.getEndereco().setCidade(rs.getString("cidade"));
                alu.getEndereco().setUf(EnumUF.valueOf(rs.getString("uf")));
            }
            comando.close();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por codigo - "+ex.getMessage());
        }
        return alu;
    }

    public List<AlunoVO> buscarPorNome(String nome) throws PersistenciaException {
        List<AlunoVO> listaAluno = new ArrayList();
        AlunoVO alu = null;

        String comandoSQL = "SELECT * FROM Aluno WHERE UPPER(nome) LIKE '" + nome.trim().toUpperCase() + "%' ORDER BY NOME LIMIT 10";

        try {
            PreparedStatement comando = conexao.getConexao().prepareStatement(comandoSQL);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                alu = new AlunoVO();
                alu.setMatricula(rs.getInt("matricula"));
                alu.setNome(rs.getString("Nome").trim());
                alu.setNomeMae(rs.getString("nomemae"));
                alu.setNomePai(rs.getString("nomepai"));
                alu.setSexo(EnumSexo.values()[rs.getInt("sexo")]);
                alu.getEndereco().setLogradouro(rs.getString("logradouro"));
                alu.getEndereco().setNumero(rs.getInt("numero"));
                alu.getEndereco().setBairro(rs.getString("bairro"));
                alu.getEndereco().setCidade(rs.getString("cidade"));
                alu.getEndereco().setUf(EnumUF.valueOf(rs.getString("uf")));
                listaAluno.add(alu);
            }
            comando.close();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na selecaoo por nome - "+ex.getMessage());
        }
        return listaAluno;
    }
}
