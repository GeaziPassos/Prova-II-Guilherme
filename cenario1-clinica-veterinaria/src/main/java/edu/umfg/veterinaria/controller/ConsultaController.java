package edu.umfg.veterinaria.controller;

import edu.umfg.veterinaria.model.Consulta;
import edu.umfg.veterinaria.service.ConsultaService;

import java.sql.SQLException;
import java.util.List;

public class ConsultaController {

    private final ConsultaService consultaService = new ConsultaService();

    public Consulta registrar(Consulta consulta) throws SQLException {
        Consulta salva = consultaService.registrar(consulta);
        System.out.println("Consulta registrada com sucesso: " + salva);
        return salva;
    }

    public Consulta buscarPorId(Integer id) throws SQLException {
        Consulta consulta = consultaService.buscarPorId(id);
        System.out.println(consulta != null ? consulta : "Consulta não encontrada.");
        return consulta;
    }

    public List<Consulta> listarTodas() throws SQLException {
        List<Consulta> consultas = consultaService.listarTodas();
        consultas.forEach(System.out::println);
        return consultas;
    }

    public List<Consulta> listarPorAnimal(Integer idAnimal) throws SQLException {
        List<Consulta> consultas = consultaService.listarPorAnimal(idAnimal);
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta registrada para este animal.");
        } else {
            consultas.forEach(System.out::println);
        }
        return consultas;
    }

    public void atualizar(Consulta consulta) throws SQLException {
        consultaService.atualizar(consulta);
        System.out.println("Consulta atualizada com sucesso: " + consulta);
    }

    public void excluir(Integer id) throws SQLException {
        consultaService.excluir(id);
        System.out.println("Consulta " + id + " excluída com sucesso.");
    }
}
