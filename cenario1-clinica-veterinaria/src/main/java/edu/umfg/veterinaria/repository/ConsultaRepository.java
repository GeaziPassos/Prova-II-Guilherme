package edu.umfg.veterinaria.repository;

import edu.umfg.veterinaria.model.Consulta;
import edu.umfg.veterinaria.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository da entidade Consulta. Concentra todo o SQL puro via JDBC
 * necessário para o CRUD de consultas.
 */
public class ConsultaRepository {

    public Consulta salvar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (id_animal, data_consulta, motivo, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getDataConsulta()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setBigDecimal(4, consulta.getValor());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    consulta.setId(rs.getInt("id"));
                }
            }
        }
        return consulta;
    }

    public Consulta buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";
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

    public List<Consulta> listarTodas() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta ORDER BY data_consulta DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                consultas.add(mapear(rs));
            }
        }
        return consultas;
    }

    public List<Consulta> listarPorAnimal(Integer idAnimal) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE id_animal = ? ORDER BY data_consulta DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapear(rs));
                }
            }
        }
        return consultas;
    }

    public void atualizar(Consulta consulta) throws SQLException {
        String sql = "UPDATE consulta SET id_animal = ?, data_consulta = ?, motivo = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getDataConsulta()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setBigDecimal(4, consulta.getValor());
            stmt.setInt(5, consulta.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Consulta mapear(ResultSet rs) throws SQLException {
        return new Consulta(
                rs.getInt("id"),
                rs.getInt("id_animal"),
                rs.getDate("data_consulta").toLocalDate(),
                rs.getString("motivo"),
                rs.getBigDecimal("valor")
        );
    }
}
