package edu.umfg.escola.model;

public class Curso {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private Integer vagasTotais;
    private Integer vagasDisponiveis;

    public Curso() {
    }

    /**
     * Construtor usado ao cadastrar um novo curso.
     * As vagas disponíveis começam iguais ao total de vagas.
     */
    public Curso(String nome, String descricao, Integer cargaHoraria, Integer vagasTotais) {
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.vagasTotais = vagasTotais;
        this.vagasDisponiveis = vagasTotais;
    }

    public Curso(Integer id, String nome, String descricao, Integer cargaHoraria, Integer vagasTotais, Integer vagasDisponiveis) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.vagasTotais = vagasTotais;
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public void setVagasTotais(Integer vagasTotais) {
        this.vagasTotais = vagasTotais;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    @Override
    public String toString() {
        return "Curso{id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", vagasTotais=" + vagasTotais +
                ", vagasDisponiveis=" + vagasDisponiveis +
                '}';
    }
}
