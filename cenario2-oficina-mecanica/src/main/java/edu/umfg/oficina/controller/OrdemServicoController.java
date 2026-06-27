package edu.umfg.oficina.controller;

import edu.umfg.oficina.model.OrdemServico;
import edu.umfg.oficina.service.OrdemServicoService;

import java.sql.SQLException;
import java.util.List;

public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();

    public OrdemServico abrir(OrdemServico ordem) throws SQLException {
        OrdemServico salva = ordemServicoService.abrir(ordem);
        System.out.println("Ordem de serviço aberta com sucesso: " + salva);
        return salva;
    }

    public void concluir(Integer idOrdem) throws SQLException {
        ordemServicoService.concluir(idOrdem);
        System.out.println("Ordem de serviço " + idOrdem + " marcada como CONCLUIDA.");
    }

    public OrdemServico buscarPorId(Integer id) throws SQLException {
        OrdemServico ordem = ordemServicoService.buscarPorId(id);
        System.out.println(ordem != null ? ordem : "Ordem de serviço não encontrada.");
        return ordem;
    }

    public List<OrdemServico> listarTodas() throws SQLException {
        List<OrdemServico> ordens = ordemServicoService.listarTodas();
        ordens.forEach(System.out::println);
        return ordens;
    }

    public List<OrdemServico> listarPorVeiculo(Integer idVeiculo) throws SQLException {
        List<OrdemServico> ordens = ordemServicoService.listarPorVeiculo(idVeiculo);
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço registrada para este veículo.");
        } else {
            ordens.forEach(System.out::println);
        }
        return ordens;
    }

    public void atualizar(OrdemServico ordem) throws SQLException {
        ordemServicoService.atualizar(ordem);
        System.out.println("Ordem de serviço atualizada com sucesso: " + ordem);
    }

    public void excluir(Integer id) throws SQLException {
        ordemServicoService.excluir(id);
        System.out.println("Ordem de serviço " + id + " excluída com sucesso.");
    }
}
