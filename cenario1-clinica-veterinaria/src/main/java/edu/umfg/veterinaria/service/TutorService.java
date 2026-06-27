package edu.umfg.veterinaria.service;

import edu.umfg.veterinaria.model.Tutor;
import edu.umfg.veterinaria.repository.TutorRepository;

import java.sql.SQLException;
import java.util.List;

public class TutorService {

    private final TutorRepository tutorRepository = new TutorRepository();

    public Tutor cadastrar(Tutor tutor) throws SQLException {
        validar(tutor);
        return tutorRepository.salvar(tutor);
    }

    public Tutor buscarPorId(Integer id) throws SQLException {
        return tutorRepository.buscarPorId(id);
    }

    public List<Tutor> listarTodos() throws SQLException {
        return tutorRepository.listarTodos();
    }

    public void atualizar(Tutor tutor) throws SQLException {
        validar(tutor);
        if (tutor.getId() == null || tutorRepository.buscarPorId(tutor.getId()) == null) {
            throw new IllegalArgumentException("Tutor com id " + tutor.getId() + " não está cadastrado.");
        }
        tutorRepository.atualizar(tutor);
    }

    public void excluir(Integer id) throws SQLException {
        tutorRepository.excluir(id);
    }

    private void validar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do tutor é obrigatório.");
        }
        if (tutor.getEndereco() == null || tutor.getEndereco().isBlank()) {
            throw new IllegalArgumentException("O endereço do tutor é obrigatório.");
        }
        if (tutor.getTelefone() == null || tutor.getTelefone().isBlank()) {
            throw new IllegalArgumentException("O telefone do tutor é obrigatório.");
        }
    }
}
