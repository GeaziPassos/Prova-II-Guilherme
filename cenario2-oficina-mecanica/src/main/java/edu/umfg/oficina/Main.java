package edu.umfg.oficina;

import edu.umfg.oficina.controller.ClienteController;
import edu.umfg.oficina.controller.OrdemServicoController;
import edu.umfg.oficina.controller.VeiculoController;
import edu.umfg.oficina.model.Cliente;
import edu.umfg.oficina.model.OrdemServico;
import edu.umfg.oficina.model.Veiculo;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Simula o fluxo completo do sistema da oficina mecânica:
 * cliente -> veículo -> ordem de serviço.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        ClienteController clienteController = new ClienteController();
        VeiculoController veiculoController = new VeiculoController();
        OrdemServicoController ordemServicoController = new OrdemServicoController();

        System.out.println("=== SIMULAÇÃO: SISTEMA DA OFICINA MECÂNICA ===\n");

        System.out.println("--- 1) Cadastrando o cliente ---");
        Cliente cliente = clienteController.cadastrar(
                new Cliente("João Pereira", "(44) 98888-2222"));

        System.out.println("\n--- 2) Cadastrando o veículo vinculado ao cliente ---");
        Veiculo veiculo = veiculoController.cadastrar(
                new Veiculo("ABC1D23", "Fiat Argo", 2022, cliente.getId()));

        System.out.println("\n--- 3) Abrindo a ordem de serviço (movimento) ---");
        OrdemServico ordem = ordemServicoController.abrir(
                new OrdemServico(veiculo.getId(), "Barulho no motor ao acelerar", new BigDecimal("350.00")));

        System.out.println("\n--- 4) Concluindo a ordem de serviço ---");
        ordemServicoController.concluir(ordem.getId());

        System.out.println("\n--- 5) Abrindo uma segunda ordem de serviço para o mesmo veículo ---");
        ordemServicoController.abrir(
                new OrdemServico(veiculo.getId(), "Troca de óleo e filtros", new BigDecimal("180.00")));

        System.out.println("\n--- 6) Histórico de manutenções do veículo placa '" + veiculo.getPlaca() + "' ---");
        ordemServicoController.listarPorVeiculo(veiculo.getId());

        System.out.println("\n--- 7) Demonstrando regra de negócio: ordem para veículo NÃO cadastrado ---");
        try {
            ordemServicoController.abrir(
                    new OrdemServico(999999, "Ordem inválida", new BigDecimal("100.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n--- 8) Demonstrando regra de negócio: valor de serviço NEGATIVO ---");
        try {
            ordemServicoController.abrir(
                    new OrdemServico(veiculo.getId(), "Ordem com valor inválido", new BigDecimal("-50.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n=== FIM DA SIMULAÇÃO ===");
    }
}
