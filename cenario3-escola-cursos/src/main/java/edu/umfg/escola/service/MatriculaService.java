package edu.umfg.escola.service;

import edu.umfg.escola.model.Aluno;
import edu.umfg.escola.model.Curso;
import edu.umfg.escola.model.Matricula;
import edu.umfg.escola.repository.AlunoRepository;
import edu.umfg.escola.repository.CursoRepository;
import edu.umfg.escola.repository.MatriculaRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Orquestra a matrícula de alunos em cursos, concentrando as regras de
 * negócio próprias do movimento deste cenário: aluno e curso precisam
 * existir, o curso precisa ter vaga disponível e o aluno não pode estar
 * matriculado duas vezes no mesmo curso.
 */
public class MatriculaService {

    private final MatriculaRepository matriculaRepository = new MatriculaRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final CursoRepository cursoRepository = new CursoRepository();

    public Matricula matricular(Matricula matricula) throws SQLException {
        validarCamposBasicos(matricula);

        Aluno aluno = alunoRepository.buscarPorId(matricula.getIdAluno());
        if (aluno == null) {
            throw new IllegalArgumentException(
                    "Não é possível matricular: aluno com id " + matricula.getIdAluno() + " não está cadastrado.");
        }

        Curso curso = cursoRepository.buscarPorId(matricula.getIdCurso());
        if (curso == null) {
            throw new IllegalArgumentException(
                    "Não é possível matricular: curso com id " + matricula.getIdCurso() + " não está cadastrado.");
        }

        if (matriculaRepository.existePorAlunoECurso(matricula.getIdAluno(), matricula.getIdCurso())) {
            throw new IllegalArgumentException(
                    "O aluno '" + aluno.getNome() + "' já está matriculado no curso '" + curso.getNome() + "'.");
        }

        if (curso.getVagasDisponiveis() == null || curso.getVagasDisponiveis() <= 0) {
            throw new IllegalArgumentException(
                    "Não há vagas disponíveis no curso '" + curso.getNome() + "'.");
        }

        Matricula salva = matriculaRepository.salvar(matricula);

        // Decrementa a vaga utilizada no curso.
        curso.setVagasDisponiveis(curso.getVagasDisponiveis() - 1);
        cursoRepository.atualizar(curso);

        return salva;
    }

    public Matricula buscarPorId(Integer id) throws SQLException {
        return matriculaRepository.buscarPorId(id);
    }

    public List<Matricula> listarTodas() throws SQLException {
        return matriculaRepository.listarTodas();
    }

    /**
     * Retorna todos os alunos matriculados em um determinado curso.
     */
    public List<Aluno> listarAlunosPorCurso(Integer idCurso) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        for (Matricula matricula : matriculaRepository.listarPorCurso(idCurso)) {
            Aluno aluno = alunoRepository.buscarPorId(matricula.getIdAluno());
            if (aluno != null) {
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    /**
     * Retorna todos os cursos em que um determinado aluno está matriculado.
     */
    public List<Curso> listarCursosPorAluno(Integer idAluno) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        for (Matricula matricula : matriculaRepository.listarPorAluno(idAluno)) {
            Curso curso = cursoRepository.buscarPorId(matricula.getIdCurso());
            if (curso != null) {
                cursos.add(curso);
            }
        }
        return cursos;
    }

    public void excluir(Integer id) throws SQLException {
        Matricula matricula = matriculaRepository.buscarPorId(id);
        if (matricula == null) {
            throw new IllegalArgumentException("Matrícula com id " + id + " não está cadastrada.");
        }
        matriculaRepository.excluir(id);

        // Libera a vaga novamente no curso ao cancelar a matrícula.
        Curso curso = cursoRepository.buscarPorId(matricula.getIdCurso());
        if (curso != null) {
            curso.setVagasDisponiveis(curso.getVagasDisponiveis() + 1);
            cursoRepository.atualizar(curso);
        }
    }

    private void validarCamposBasicos(Matricula matricula) {
        if (matricula.getIdAluno() == null) {
            throw new IllegalArgumentException("A matrícula precisa estar vinculada a um aluno.");
        }
        if (matricula.getIdCurso() == null) {
            throw new IllegalArgumentException("A matrícula precisa estar vinculada a um curso.");
        }
        if (matricula.getDataMatricula() == null) {
            throw new IllegalArgumentException("A data da matrícula é obrigatória.");
        }
        if (matricula.getValorPago() == null || matricula.getValorPago().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor pago na matrícula não pode ser negativo.");
        }
    }
}
