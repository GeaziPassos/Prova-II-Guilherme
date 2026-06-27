package edu.umfg.escola.controller;

import edu.umfg.escola.model.Aluno;
import edu.umfg.escola.service.AlunoService;

import java.sql.SQLException;
import java.util.List;

public class AlunoController {

    private final AlunoService alunoService = new AlunoService();

    public Aluno cadastrar(Aluno aluno) throws SQLException {
        Aluno salvo = alunoService.cadastrar(aluno);
        System.out.println("Aluno cadastrado com sucesso: " + salvo);
        return salvo;
    }

    public Aluno buscarPorId(Integer id) throws SQLException {
        Aluno aluno = alunoService.buscarPorId(id);
        System.out.println(aluno != null ? aluno : "Aluno não encontrado.");
        return aluno;
    }

    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> alunos = alunoService.listarTodos();
        alunos.forEach(System.out::println);
        return alunos;
    }

    public void atualizar(Aluno aluno) throws SQLException {
        alunoService.atualizar(aluno);
        System.out.println("Aluno atualizado com sucesso: " + aluno);
    }

    public void excluir(Integer id) throws SQLException {
        alunoService.excluir(id);
        System.out.println("Aluno " + id + " excluído com sucesso.");
    }
}
