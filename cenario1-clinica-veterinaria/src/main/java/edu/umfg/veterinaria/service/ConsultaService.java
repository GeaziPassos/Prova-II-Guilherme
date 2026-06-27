package edu.umfg.veterinaria.service;

import edu.umfg.veterinaria.model.Consulta;
import edu.umfg.veterinaria.repository.AnimalRepository;
import edu.umfg.veterinaria.repository.ConsultaRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository consultaRepository = new ConsultaRepository();
    private final AnimalRepository animalRepository = new AnimalRepository();

    public Consulta registrar(Consulta consulta) throws SQLException {
        validar(consulta);
        if (animalRepository.buscarPorId(consulta.getIdAnimal()) == null) {
            throw new IllegalArgumentException(
                    "Não é possível registrar a consulta: animal com id " + consulta.getIdAnimal() + " não está cadastrado.");
        }
        return consultaRepository.salvar(consulta);
    }

    public Consulta buscarPorId(Integer id) throws SQLException {
        return consultaRepository.buscarPorId(id);
    }

    public List<Consulta> listarTodas() throws SQLException {
        return consultaRepository.listarTodas();
    }

    public List<Consulta> listarPorAnimal(Integer idAnimal) throws SQLException {
        return consultaRepository.listarPorAnimal(idAnimal);
    }

    public void atualizar(Consulta consulta) throws SQLException {
        validar(consulta);
        if (consulta.getId() == null || consultaRepository.buscarPorId(consulta.getId()) == null) {
            throw new IllegalArgumentException("Consulta com id " + consulta.getId() + " não está cadastrada.");
        }
        consultaRepository.atualizar(consulta);
    }

    public void excluir(Integer id) throws SQLException {
        consultaRepository.excluir(id);
    }

    private void validar(Consulta consulta) {
        if (consulta.getIdAnimal() == null) {
            throw new IllegalArgumentException("A consulta precisa estar vinculada a um animal.");
        }
        if (consulta.getDataConsulta() == null) {
            throw new IllegalArgumentException("A data da consulta é obrigatória.");
        }
        if (consulta.getMotivo() == null || consulta.getMotivo().isBlank()) {
            throw new IllegalArgumentException("O motivo da consulta é obrigatório.");
        }
        if (consulta.getValor() == null || consulta.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da consulta não pode ser negativo.");
        }
    }
}
