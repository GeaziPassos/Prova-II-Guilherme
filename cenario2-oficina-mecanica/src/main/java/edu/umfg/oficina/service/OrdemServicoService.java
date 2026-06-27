package edu.umfg.oficina.service;

import edu.umfg.oficina.model.OrdemServico;
import edu.umfg.oficina.model.StatusOrdem;
import edu.umfg.oficina.repository.OrdemServicoRepository;
import edu.umfg.oficina.repository.VeiculoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository = new OrdemServicoRepository();
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public OrdemServico abrir(OrdemServico ordem) throws SQLException {
        validar(ordem);
        if (veiculoRepository.buscarPorId(ordem.getIdVeiculo()) == null) {
            throw new IllegalArgumentException(
                    "Não é possível abrir a ordem de serviço: veículo com id " + ordem.getIdVeiculo() + " não está cadastrado.");
        }
        ordem.setStatus(StatusOrdem.ABERTA);
        return ordemServicoRepository.salvar(ordem);
    }

    public void concluir(Integer idOrdem) throws SQLException {
        OrdemServico ordem = ordemServicoRepository.buscarPorId(idOrdem);
        if (ordem == null) {
            throw new IllegalArgumentException("Ordem de serviço com id " + idOrdem + " não está cadastrada.");
        }
        ordem.setStatus(StatusOrdem.CONCLUIDA);
        ordemServicoRepository.atualizar(ordem);
    }

    public OrdemServico buscarPorId(Integer id) throws SQLException {
        return ordemServicoRepository.buscarPorId(id);
    }

    public List<OrdemServico> listarTodas() throws SQLException {
        return ordemServicoRepository.listarTodas();
    }

    public List<OrdemServico> listarPorVeiculo(Integer idVeiculo) throws SQLException {
        return ordemServicoRepository.listarPorVeiculo(idVeiculo);
    }

    public void atualizar(OrdemServico ordem) throws SQLException {
        validar(ordem);
        if (ordem.getId() == null || ordemServicoRepository.buscarPorId(ordem.getId()) == null) {
            throw new IllegalArgumentException("Ordem de serviço com id " + ordem.getId() + " não está cadastrada.");
        }
        ordemServicoRepository.atualizar(ordem);
    }

    public void excluir(Integer id) throws SQLException {
        ordemServicoRepository.excluir(id);
    }

    private void validar(OrdemServico ordem) {
        if (ordem.getIdVeiculo() == null) {
            throw new IllegalArgumentException("A ordem de serviço precisa estar vinculada a um veículo.");
        }
        if (ordem.getDescricaoProblema() == null || ordem.getDescricaoProblema().isBlank()) {
            throw new IllegalArgumentException("A descrição do problema é obrigatória.");
        }
        if (ordem.getValorServico() == null || ordem.getValorServico().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
        }
    }
}
