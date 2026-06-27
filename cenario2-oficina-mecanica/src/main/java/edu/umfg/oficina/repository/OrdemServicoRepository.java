package edu.umfg.oficina.repository;

import edu.umfg.oficina.model.OrdemServico;
import edu.umfg.oficina.model.StatusOrdem;
import edu.umfg.oficina.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoRepository {

    public OrdemServico salvar(OrdemServico ordem) throws SQLException {
        String sql = "INSERT INTO ordem_servico (id_veiculo, descricao_problema, valor_servico, status) " +
                "VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordem.getIdVeiculo());
            stmt.setString(2, ordem.getDescricaoProblema());
            stmt.setBigDecimal(3, ordem.getValorServico());
            stmt.setString(4, ordem.getStatus().name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ordem.setId(rs.getInt("id"));
                }
            }
        }
        return ordem;
    }

    public OrdemServico buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
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

    public List<OrdemServico> listarTodas() throws SQLException {
        List<OrdemServico> ordens = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico ORDER BY id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ordens.add(mapear(rs));
            }
        }
        return ordens;
    }

    public List<OrdemServico> listarPorVeiculo(Integer idVeiculo) throws SQLException {
        List<OrdemServico> ordens = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico WHERE id_veiculo = ? ORDER BY id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVeiculo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ordens.add(mapear(rs));
                }
            }
        }
        return ordens;
    }

    public void atualizar(OrdemServico ordem) throws SQLException {
        String sql = "UPDATE ordem_servico SET id_veiculo = ?, descricao_problema = ?, valor_servico = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordem.getIdVeiculo());
            stmt.setString(2, ordem.getDescricaoProblema());
            stmt.setBigDecimal(3, ordem.getValorServico());
            stmt.setString(4, ordem.getStatus().name());
            stmt.setInt(5, ordem.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private OrdemServico mapear(ResultSet rs) throws SQLException {
        return new OrdemServico(
                rs.getInt("id"),
                rs.getInt("id_veiculo"),
                rs.getString("descricao_problema"),
                rs.getBigDecimal("valor_servico"),
                StatusOrdem.valueOf(rs.getString("status"))
        );
    }
}
