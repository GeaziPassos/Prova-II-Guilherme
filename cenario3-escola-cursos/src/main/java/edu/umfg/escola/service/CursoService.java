package edu.umfg.escola.service;

import edu.umfg.escola.model.Curso;
import edu.umfg.escola.repository.CursoRepository;

import java.sql.SQLException;
import java.util.List;

public class CursoService {

    private final CursoRepository cursoRepository = new CursoRepository();

    public Curso cadastrar(Curso curso) throws SQLException {
        validar(curso);
        return cursoRepository.salvar(curso);
    }

    public Curso buscarPorId(Integer id) throws SQLException {
        return cursoRepository.buscarPorId(id);
    }

    public List<Curso> listarTodos() throws SQLException {
        return cursoRepository.listarTodos();
    }

    public void atualizar(Curso curso) throws SQLException {
        validar(curso);
        if (curso.getId() == null || cursoRepository.buscarPorId(curso.getId()) == null) {
            throw new IllegalArgumentException("Curso com id " + curso.getId() + " não está cadastrado.");
        }
        cursoRepository.atualizar(curso);
    }

    public void excluir(Integer id) throws SQLException {
        cursoRepository.excluir(id);
    }

    private void validar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (curso.getCargaHoraria() == null || curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("A carga horária do curso deve ser maior que zero.");
        }
        if (curso.getVagasTotais() == null || curso.getVagasTotais() <= 0) {
            throw new IllegalArgumentException("O número máximo de vagas do curso deve ser maior que zero.");
        }
    }
}
