package edu.umfg.veterinaria.repository;

import edu.umfg.veterinaria.model.Tutor;
import edu.umfg.veterinaria.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository da entidade Tutor. Concentra todo o SQL puro via JDBC
 * necessário para o CRUD de tutores.
 */
public class TutorRepository {

    public Tutor salvar(Tutor tutor) throws SQLException {
        String sql = "INSERT INTO tutor (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tutor.setId(rs.getInt("id"));
                }
            }
        }
        return tutor;
    }

    public Tutor buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM tutor WHERE id = ?";
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

    public List<Tutor> listarTodos() throws SQLException {
        List<Tutor> tutores = new ArrayList<>();
        String sql = "SELECT * FROM tutor ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tutores.add(mapear(rs));
            }
        }
        return tutores;
    }

    public void atualizar(Tutor tutor) throws SQLException {
        String sql = "UPDATE tutor SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            stmt.setInt(4, tutor.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Tutor mapear(ResultSet rs) throws SQLException {
        return new Tutor(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("endereco"),
                rs.getString("telefone")
        );
    }
}
