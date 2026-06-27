package edu.umfg.oficina.controller;

import edu.umfg.oficina.model.Cliente;
import edu.umfg.oficina.service.ClienteService;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private final ClienteService clienteService = new ClienteService();

    public Cliente cadastrar(Cliente cliente) throws SQLException {
        Cliente salvo = clienteService.cadastrar(cliente);
        System.out.println("Cliente cadastrado com sucesso: " + salvo);
        return salvo;
    }

    public Cliente buscarPorId(Integer id) throws SQLException {
        Cliente cliente = clienteService.buscarPorId(id);
        System.out.println(cliente != null ? cliente : "Cliente não encontrado.");
        return cliente;
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = clienteService.listarTodos();
        clientes.forEach(System.out::println);
        return clientes;
    }

    public void atualizar(Cliente cliente) throws SQLException {
        clienteService.atualizar(cliente);
        System.out.println("Cliente atualizado com sucesso: " + cliente);
    }

    public void excluir(Integer id) throws SQLException {
        clienteService.excluir(id);
        System.out.println("Cliente " + id + " excluído com sucesso.");
    }
}
