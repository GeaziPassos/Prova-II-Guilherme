package edu.umfg.oficina.controller;

import edu.umfg.oficina.model.Veiculo;
import edu.umfg.oficina.service.VeiculoService;

import java.sql.SQLException;
import java.util.List;

public class VeiculoController {

    private final VeiculoService veiculoService = new VeiculoService();

    public Veiculo cadastrar(Veiculo veiculo) throws SQLException {
        Veiculo salvo = veiculoService.cadastrar(veiculo);
        System.out.println("Veículo cadastrado com sucesso: " + salvo);
        return salvo;
    }

    public Veiculo buscarPorId(Integer id) throws SQLException {
        Veiculo veiculo = veiculoService.buscarPorId(id);
        System.out.println(veiculo != null ? veiculo : "Veículo não encontrado.");
        return veiculo;
    }

    public List<Veiculo> listarTodos() throws SQLException {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        veiculos.forEach(System.out::println);
        return veiculos;
    }

    public List<Veiculo> listarPorCliente(Integer idCliente) throws SQLException {
        List<Veiculo> veiculos = veiculoService.listarPorCliente(idCliente);
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado para este cliente.");
        } else {
            veiculos.forEach(System.out::println);
        }
        return veiculos;
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        veiculoService.atualizar(veiculo);
        System.out.println("Veículo atualizado com sucesso: " + veiculo);
    }

    public void excluir(Integer id) throws SQLException {
        veiculoService.excluir(id);
        System.out.println("Veículo " + id + " excluído com sucesso.");
    }
}
