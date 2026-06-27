package edu.umfg.oficina.model;

public class Veiculo {

    private Integer id;
    private String placa;
    private String modelo;
    private Integer ano;
    private Integer idCliente;

    public Veiculo() {
    }

    public Veiculo(String placa, String modelo, Integer ano, Integer idCliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.idCliente = idCliente;
    }

    public Veiculo(Integer id, String placa, String modelo, Integer ano, Integer idCliente) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.idCliente = idCliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Veiculo{id=" + id +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", idCliente=" + idCliente +
                '}';
    }
}
