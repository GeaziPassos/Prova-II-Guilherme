package edu.umfg.escola.service;

import edu.umfg.escola.model.Aluno;
import edu.umfg.escola.repository.AlunoRepository;

import java.sql.SQLException;
import java.util.List;

public class AlunoService {

    private final AlunoRepository alunoRepository = new AlunoRepository();

    public Aluno cadastrar(Aluno aluno) throws SQLException {
        validar(aluno);
        return alunoRepository.salvar(aluno);
    }

    public Aluno buscarPorId(Integer id) throws SQLException {
        return alunoRepository.buscarPorId(id);
    }

    public List<Aluno> listarTodos() throws SQLException {
        return alunoRepository.listarTodos();
    }

    public void atualizar(Aluno aluno) throws SQLException {
        validar(aluno);
        if (aluno.getId() == null || alunoRepository.buscarPorId(aluno.getId()) == null) {
            throw new IllegalArgumentException("Aluno com id " + aluno.getId() + " não está cadastrado.");
        }
        alunoRepository.atualizar(aluno);
    }

    public void excluir(Integer id) throws SQLException {
        alunoRepository.excluir(id);
    }

    private void validar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }
        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail do aluno é obrigatório.");
        }
        if (aluno.getTelefone() == null || aluno.getTelefone().isBlank()) {
            throw new IllegalArgumentException("O telefone do aluno é obrigatório.");
        }
    }
}
