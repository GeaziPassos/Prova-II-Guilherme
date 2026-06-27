package edu.umfg.escola.repository;

import edu.umfg.escola.model.Aluno;
import edu.umfg.escola.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {

    public Aluno salvar(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (nome, email, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aluno.setId(rs.getInt("id"));
                }
            }
        }
        return aluno;
    }

    public Aluno buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alunos.add(mapear(rs));
            }
        }
        return alunos;
    }

    public void atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());
            stmt.setInt(4, aluno.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Aluno mapear(ResultSet rs) throws SQLException {
        return new Aluno(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("telefone")
        );
    }
}
