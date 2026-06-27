package edu.umfg.oficina.service;

import edu.umfg.oficina.model.Cliente;
import edu.umfg.oficina.repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository = new ClienteRepository();

    public Cliente cadastrar(Cliente cliente) throws SQLException {
        validar(cliente);
        return clienteRepository.salvar(cliente);
    }

    public Cliente buscarPorId(Integer id) throws SQLException {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteRepository.listarTodos();
    }

    public void atualizar(Cliente cliente) throws SQLException {
        validar(cliente);
        if (cliente.getId() == null || clienteRepository.buscarPorId(cliente.getId()) == null) {
            throw new IllegalArgumentException("Cliente com id " + cliente.getId() + " não está cadastrado.");
        }
        clienteRepository.atualizar(cliente);
    }

    public void excluir(Integer id) throws SQLException {
        clienteRepository.excluir(id);
    }

    private void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new IllegalArgumentException("O telefone do cliente é obrigatório.");
        }
    }
}
