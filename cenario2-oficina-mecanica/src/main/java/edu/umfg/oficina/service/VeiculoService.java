package edu.umfg.oficina.service;

import edu.umfg.oficina.model.Veiculo;
import edu.umfg.oficina.repository.ClienteRepository;
import edu.umfg.oficina.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;

public class VeiculoService {

    private final VeiculoRepository veiculoRepository = new VeiculoRepository();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    public Veiculo cadastrar(Veiculo veiculo) throws SQLException {
        validar(veiculo);
        if (clienteRepository.buscarPorId(veiculo.getIdCliente()) == null) {
            throw new IllegalArgumentException(
                    "Não é possível cadastrar o veículo: cliente com id " + veiculo.getIdCliente() + " não está cadastrado.");
        }
        return veiculoRepository.salvar(veiculo);
    }

    public Veiculo buscarPorId(Integer id) throws SQLException {
        return veiculoRepository.buscarPorId(id);
    }

    public List<Veiculo> listarTodos() throws SQLException {
        return veiculoRepository.listarTodos();
    }

    public List<Veiculo> listarPorCliente(Integer idCliente) throws SQLException {
        return veiculoRepository.listarPorCliente(idCliente);
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        validar(veiculo);
        if (veiculo.getId() == null || veiculoRepository.buscarPorId(veiculo.getId()) == null) {
            throw new IllegalArgumentException("Veículo com id " + veiculo.getId() + " não está cadastrado.");
        }
        veiculoRepository.atualizar(veiculo);
    }

    public void excluir(Integer id) throws SQLException {
        veiculoRepository.excluir(id);
    }

    private void validar(Veiculo veiculo) {
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()) {
            throw new IllegalArgumentException("A placa do veículo é obrigatória.");
        }
        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank()) {
            throw new IllegalArgumentException("O modelo do veículo é obrigatório.");
        }
        if (veiculo.getAno() == null) {
            throw new IllegalArgumentException("O ano do veículo é obrigatório.");
        }
        if (veiculo.getIdCliente() == null) {
            throw new IllegalArgumentException("O veículo precisa estar vinculado a um cliente.");
        }
    }
}
