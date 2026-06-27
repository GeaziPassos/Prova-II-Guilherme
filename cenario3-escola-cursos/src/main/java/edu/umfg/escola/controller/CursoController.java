package edu.umfg.escola.controller;

import edu.umfg.escola.model.Curso;
import edu.umfg.escola.service.CursoService;

import java.sql.SQLException;
import java.util.List;

public class CursoController {

    private final CursoService cursoService = new CursoService();

    public Curso cadastrar(Curso curso) throws SQLException {
        Curso salvo = cursoService.cadastrar(curso);
        System.out.println("Curso cadastrado com sucesso: " + salvo);
        return salvo;
    }

    public Curso buscarPorId(Integer id) throws SQLException {
        Curso curso = cursoService.buscarPorId(id);
        System.out.println(curso != null ? curso : "Curso não encontrado.");
        return curso;
    }

    public List<Curso> listarTodos() throws SQLException {
        List<Curso> cursos = cursoService.listarTodos();
        cursos.forEach(System.out::println);
        return cursos;
    }

    public void atualizar(Curso curso) throws SQLException {
        cursoService.atualizar(curso);
        System.out.println("Curso atualizado com sucesso: " + curso);
    }

    public void excluir(Integer id) throws SQLException {
        cursoService.excluir(id);
        System.out.println("Curso " + id + " excluído com sucesso.");
    }
}
