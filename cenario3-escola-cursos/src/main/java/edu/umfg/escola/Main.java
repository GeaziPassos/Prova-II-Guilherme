package edu.umfg.escola;

import edu.umfg.escola.controller.AlunoController;
import edu.umfg.escola.controller.CursoController;
import edu.umfg.escola.controller.MatriculaController;
import edu.umfg.escola.model.Aluno;
import edu.umfg.escola.model.Curso;
import edu.umfg.escola.model.Matricula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Simula o fluxo completo do sistema da escola de cursos livres:
 * aluno -> curso -> matrícula, incluindo tentativas inválidas de matrícula
 * (sem vaga e duplicada) para demonstrar que as regras de negócio funcionam.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        AlunoController alunoController = new AlunoController();
        CursoController cursoController = new CursoController();
        MatriculaController matriculaController = new MatriculaController();

        System.out.println("=== SIMULAÇÃO: SISTEMA DA ESCOLA DE CURSOS LIVRES ===\n");

        System.out.println("--- 1) Cadastrando os alunos ---");
        Aluno ana = alunoController.cadastrar(
                new Aluno("Ana Lima", "ana.lima@email.com", "(44) 99777-1234"));
        Aluno bruno = alunoController.cadastrar(
                new Aluno("Bruno Costa", "bruno.costa@email.com", "(44) 99777-5678"));

        System.out.println("\n--- 2) Cadastrando os cursos ---");
        Curso excel = cursoController.cadastrar(
                new Curso("Excel Avançado", "Planilhas, fórmulas e dashboards", 20, 1));
        Curso ingles = cursoController.cadastrar(
                new Curso("Inglês Básico", "Conversação e gramática essencial", 60, 3));

        System.out.println("\n--- 3) Matriculando Ana no curso de Excel Avançado (movimento) ---");
        matriculaController.matricular(
                new Matricula(ana.getId(), excel.getId(), LocalDate.now(), new BigDecimal("250.00")));

        System.out.println("\n--- 4) Matriculando Ana também no curso de Inglês Básico ---");
        matriculaController.matricular(
                new Matricula(ana.getId(), ingles.getId(), LocalDate.now(), new BigDecimal("300.00")));

        System.out.println("\n--- 5) Tentando matricular Bruno no Excel Avançado (curso SEM vaga) ---");
        try {
            matriculaController.matricular(
                    new Matricula(bruno.getId(), excel.getId(), LocalDate.now(), new BigDecimal("250.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n--- 6) Tentando matricular Ana NOVAMENTE no Excel Avançado (matrícula DUPLICADA) ---");
        try {
            matriculaController.matricular(
                    new Matricula(ana.getId(), excel.getId(), LocalDate.now(), new BigDecimal("250.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n--- 7) Demonstrando regra de negócio: matrícula com valor pago NEGATIVO ---");
        try {
            matriculaController.matricular(
                    new Matricula(bruno.getId(), ingles.getId(), LocalDate.now(), new BigDecimal("-30.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n--- 8) Alunos matriculados no curso '" + excel.getNome() + "' ---");
        matriculaController.listarAlunosPorCurso(excel.getId());

        System.out.println("\n--- 9) Cursos em que '" + ana.getNome() + "' está matriculada ---");
        matriculaController.listarCursosPorAluno(ana.getId());

        System.out.println("\n=== FIM DA SIMULAÇÃO ===");
    }
}
