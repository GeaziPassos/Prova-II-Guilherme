package edu.umfg.veterinaria.service;

import edu.umfg.veterinaria.model.Animal;
import edu.umfg.veterinaria.repository.AnimalRepository;
import edu.umfg.veterinaria.repository.TutorRepository;

import java.sql.SQLException;
import java.util.List;

public class AnimalService {

    private final AnimalRepository animalRepository = new AnimalRepository();
    private final TutorRepository tutorRepository = new TutorRepository();

    public Animal cadastrar(Animal animal) throws SQLException {
        validar(animal);
        if (tutorRepository.buscarPorId(animal.getIdTutor()) == null) {
            throw new IllegalArgumentException(
                    "Não é possível cadastrar o animal: tutor com id " + animal.getIdTutor() + " não está cadastrado.");
        }
        return animalRepository.salvar(animal);
    }

    public Animal buscarPorId(Integer id) throws SQLException {
        return animalRepository.buscarPorId(id);
    }

    public List<Animal> listarTodos() throws SQLException {
        return animalRepository.listarTodos();
    }

    public List<Animal> listarPorTutor(Integer idTutor) throws SQLException {
        return animalRepository.listarPorTutor(idTutor);
    }

    public void atualizar(Animal animal) throws SQLException {
        validar(animal);
        if (animal.getId() == null || animalRepository.buscarPorId(animal.getId()) == null) {
            throw new IllegalArgumentException("Animal com id " + animal.getId() + " não está cadastrado.");
        }
        animalRepository.atualizar(animal);
    }

    public void excluir(Integer id) throws SQLException {
        animalRepository.excluir(id);
    }

    private void validar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do animal é obrigatório.");
        }
        if (animal.getEspecie() == null || animal.getEspecie().isBlank()) {
            throw new IllegalArgumentException("A espécie do animal é obrigatória.");
        }
        if (animal.getRaca() == null || animal.getRaca().isBlank()) {
            throw new IllegalArgumentException("A raça do animal é obrigatória.");
        }
        if (animal.getIdTutor() == null) {
            throw new IllegalArgumentException("O animal precisa estar vinculado a um tutor.");
        }
    }
}
