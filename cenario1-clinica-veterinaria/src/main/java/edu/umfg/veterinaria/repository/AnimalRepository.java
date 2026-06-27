package edu.umfg.veterinaria.repository;

import edu.umfg.veterinaria.model.Animal;
import edu.umfg.veterinaria.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository da entidade Animal. Concentra todo o SQL puro via JDBC
 * necessário para o CRUD de animais.
 */
public class AnimalRepository {

    public Animal salvar(Animal animal) throws SQLException {
        String sql = "INSERT INTO animal (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdTutor());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    animal.setId(rs.getInt("id"));
                }
            }
        }
        return animal;
    }

    public Animal buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM animal WHERE id = ?";
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

    public List<Animal> listarTodos() throws SQLException {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animal ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                animais.add(mapear(rs));
            }
        }
        return animais;
    }

    public List<Animal> listarPorTutor(Integer idTutor) throws SQLException {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animal WHERE id_tutor = ? ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTutor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animais.add(mapear(rs));
                }
            }
        }
        return animais;
    }

    public void atualizar(Animal animal) throws SQLException {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, id_tutor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdTutor());
            stmt.setInt(5, animal.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Animal mapear(ResultSet rs) throws SQLException {
        return new Animal(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("especie"),
                rs.getString("raca"),
                rs.getInt("id_tutor")
        );
    }
}
