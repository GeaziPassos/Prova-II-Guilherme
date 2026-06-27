package edu.umfg.escola.controller;

import edu.umfg.escola.model.Aluno;
import edu.umfg.escola.model.Curso;
import edu.umfg.escola.model.Matricula;
import edu.umfg.escola.service.MatriculaService;

import java.sql.SQLException;
import java.util.List;

public class MatriculaController {

    private final MatriculaService matriculaService = new MatriculaService();

    public Matricula matricular(Matricula matricula) throws SQLException {
        Matricula salva = matriculaService.matricular(matricula);
        System.out.println("Matrícula realizada com sucesso: " + salva);
        return salva;
    }

    public Matricula buscarPorId(Integer id) throws SQLException {
        Matricula matricula = matriculaService.buscarPorId(id);
        System.out.println(matricula != null ? matricula : "Matrícula não encontrada.");
        return matricula;
    }

    public List<Matricula> listarTodas() throws SQLException {
        List<Matricula> matriculas = matriculaService.listarTodas();
        matriculas.forEach(System.out::println);
        return matriculas;
    }

    public List<Aluno> listarAlunosPorCurso(Integer idCurso) throws SQLException {
        List<Aluno> alunos = matriculaService.listarAlunosPorCurso(idCurso);
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno matriculado neste curso.");
        } else {
            alunos.forEach(System.out::println);
        }
        return alunos;
    }

    public List<Curso> listarCursosPorAluno(Integer idAluno) throws SQLException {
        List<Curso> cursos = matriculaService.listarCursosPorAluno(idAluno);
        if (cursos.isEmpty()) {
            System.out.println("Este aluno não está matriculado em nenhum curso.");
        } else {
            cursos.forEach(System.out::println);
        }
        return cursos;
    }

    public void excluir(Integer id) throws SQLException {
        matriculaService.excluir(id);
        System.out.println("Matrícula " + id + " excluída com sucesso (vaga liberada).");
    }
}
