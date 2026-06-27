package edu.umfg.escola.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Matricula {

    private Integer id;
    private Integer idAluno;
    private Integer idCurso;
    private LocalDate dataMatricula;
    private BigDecimal valorPago;

    public Matricula() {
    }

    public Matricula(Integer idAluno, Integer idCurso, LocalDate dataMatricula, BigDecimal valorPago) {
        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.dataMatricula = dataMatricula;
        this.valorPago = valorPago;
    }

    public Matricula(Integer id, Integer idAluno, Integer idCurso, LocalDate dataMatricula, BigDecimal valorPago) {
        this.id = id;
        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.dataMatricula = dataMatricula;
        this.valorPago = valorPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    @Override
    public String toString() {
        return "Matricula{id=" + id +
                ", idAluno=" + idAluno +
                ", idCurso=" + idCurso +
                ", dataMatricula=" + dataMatricula +
                ", valorPago=" + valorPago +
                '}';
    }
}
