package edu.umfg.escola.repository;

import edu.umfg.escola.model.Matricula;
import edu.umfg.escola.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaRepository {

    public Matricula salvar(Matricula matricula) throws SQLException {
        String sql = "INSERT INTO matricula (id_aluno, id_curso, data_matricula, valor_pago) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula.getIdAluno());
            stmt.setInt(2, matricula.getIdCurso());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setBigDecimal(4, matricula.getValorPago());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    matricula.setId(rs.getInt("id"));
                }
            }
        }
        return matricula;
    }

    public Matricula buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM matricula WHERE id = ?";
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

    public List<Matricula> listarTodas() throws SQLException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT * FROM matricula ORDER BY data_matricula DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                matriculas.add(mapear(rs));
            }
        }
        return matriculas;
    }

    public List<Matricula> listarPorAluno(Integer idAluno) throws SQLException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT * FROM matricula WHERE id_aluno = ? ORDER BY data_matricula DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    matriculas.add(mapear(rs));
                }
            }
        }
        return matriculas;
    }

    public List<Matricula> listarPorCurso(Integer idCurso) throws SQLException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT * FROM matricula WHERE id_curso = ? ORDER BY data_matricula DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    matriculas.add(mapear(rs));
                }
            }
        }
        return matriculas;
    }

    /**
     * Verifica se já existe matrícula do aluno informado no curso informado.
     * Usado pelo service para impedir matrícula duplicada.
     */
    public boolean existePorAlunoECurso(Integer idAluno, Integer idCurso) throws SQLException {
        String sql = "SELECT 1 FROM matricula WHERE id_aluno = ? AND id_curso = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void atualizar(Matricula matricula) throws SQLException {
        String sql = "UPDATE matricula SET id_aluno = ?, id_curso = ?, data_matricula = ?, valor_pago = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula.getIdAluno());
            stmt.setInt(2, matricula.getIdCurso());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setBigDecimal(4, matricula.getValorPago());
            stmt.setInt(5, matricula.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM matricula WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Matricula mapear(ResultSet rs) throws SQLException {
        return new Matricula(
                rs.getInt("id"),
                rs.getInt("id_aluno"),
                rs.getInt("id_curso"),
                rs.getDate("data_matricula").toLocalDate(),
                rs.getBigDecimal("valor_pago")
        );
    }
}
