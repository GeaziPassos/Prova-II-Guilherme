package edu.umfg.veterinaria.controller;

import edu.umfg.veterinaria.model.Tutor;
import edu.umfg.veterinaria.service.TutorService;

import java.sql.SQLException;
import java.util.List;

public class TutorController {

    private final TutorService tutorService = new TutorService();

    public Tutor cadastrar(Tutor tutor) throws SQLException {
        Tutor salvo = tutorService.cadastrar(tutor);
        System.out.println("Tutor cadastrado com sucesso: " + salvo);
        return salvo;
    }

    public Tutor buscarPorId(Integer id) throws SQLException {
        Tutor tutor = tutorService.buscarPorId(id);
        System.out.println(tutor != null ? tutor : "Tutor não encontrado.");
        return tutor;
    }

    public List<Tutor> listarTodos() throws SQLException {
        List<Tutor> tutores = tutorService.listarTodos();
        tutores.forEach(System.out::println);
        return tutores;
    }

    public void atualizar(Tutor tutor) throws SQLException {
        tutorService.atualizar(tutor);
        System.out.println("Tutor atualizado com sucesso: " + tutor);
    }

    public void excluir(Integer id) throws SQLException {
        tutorService.excluir(id);
        System.out.println("Tutor " + id + " excluído com sucesso.");
    }
}
