package edu.umfg.veterinaria.controller;

import edu.umfg.veterinaria.model.Animal;
import edu.umfg.veterinaria.service.AnimalService;

import java.sql.SQLException;
import java.util.List;

public class AnimalController {

    private final AnimalService animalService = new AnimalService();

    public Animal cadastrar(Animal animal) throws SQLException {
        Animal salvo = animalService.cadastrar(animal);
        System.out.println("Animal cadastrado com sucesso: " + salvo);
        return salvo;
    }

    public Animal buscarPorId(Integer id) throws SQLException {
        Animal animal = animalService.buscarPorId(id);
        System.out.println(animal != null ? animal : "Animal não encontrado.");
        return animal;
    }

    public List<Animal> listarTodos() throws SQLException {
        List<Animal> animais = animalService.listarTodos();
        animais.forEach(System.out::println);
        return animais;
    }

    public List<Animal> listarPorTutor(Integer idTutor) throws SQLException {
        List<Animal> animais = animalService.listarPorTutor(idTutor);
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado para este tutor.");
        } else {
            animais.forEach(System.out::println);
        }
        return animais;
    }

    public void atualizar(Animal animal) throws SQLException {
        animalService.atualizar(animal);
        System.out.println("Animal atualizado com sucesso: " + animal);
    }

    public void excluir(Integer id) throws SQLException {
        animalService.excluir(id);
        System.out.println("Animal " + id + " excluído com sucesso.");
    }
}
