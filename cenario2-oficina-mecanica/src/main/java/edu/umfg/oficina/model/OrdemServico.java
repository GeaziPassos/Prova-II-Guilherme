package edu.umfg.oficina.model;

import java.math.BigDecimal;

public class OrdemServico {

    private Integer id;
    private Integer idVeiculo;
    private String descricaoProblema;
    private BigDecimal valorServico;
    private StatusOrdem status;

    public OrdemServico() {
    }

    /**
     * Construtor usado ao abrir uma nova ordem de serviço.
     * O status sempre começa como ABERTA.
     */
    public OrdemServico(Integer idVeiculo, String descricaoProblema, BigDecimal valorServico) {
        this.idVeiculo = idVeiculo;
        this.descricaoProblema = descricaoProblema;
        this.valorServico = valorServico;
        this.status = StatusOrdem.ABERTA;
    }

    public OrdemServico(Integer id, Integer idVeiculo, String descricaoProblema, BigDecimal valorServico, StatusOrdem status) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.descricaoProblema = descricaoProblema;
        this.valorServico = valorServico;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrdemServico{id=" + id +
                ", idVeiculo=" + idVeiculo +
                ", descricaoProblema='" + descricaoProblema + '\'' +
                ", valorServico=" + valorServico +
                ", status=" + status +
                '}';
    }
}
