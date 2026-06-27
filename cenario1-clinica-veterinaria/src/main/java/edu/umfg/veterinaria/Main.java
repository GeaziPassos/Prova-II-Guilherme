package edu.umfg.veterinaria;

import edu.umfg.veterinaria.controller.AnimalController;
import edu.umfg.veterinaria.controller.ConsultaController;
import edu.umfg.veterinaria.controller.TutorController;
import edu.umfg.veterinaria.model.Animal;
import edu.umfg.veterinaria.model.Consulta;
import edu.umfg.veterinaria.model.Tutor;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Simula o fluxo completo do sistema da clínica veterinária:
 * tutor -> animal -> consulta.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        TutorController tutorController = new TutorController();
        AnimalController animalController = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        System.out.println("=== SIMULAÇÃO: SISTEMA DA CLÍNICA VETERINÁRIA ===\n");

        System.out.println("--- 1) Cadastrando o tutor ---");
        Tutor tutor = tutorController.cadastrar(
                new Tutor("Maria Souza", "Rua das Flores, 123 - Centro", "(44) 99999-1111"));

        System.out.println("\n--- 2) Cadastrando o animal vinculado ao tutor ---");
        Animal animal = animalController.cadastrar(
                new Animal("Rex", "Cachorro", "Labrador", tutor.getId()));

        System.out.println("\n--- 3) Registrando a consulta do animal (movimento) ---");
        Consulta consulta = consultaController.registrar(
                new Consulta(animal.getId(), LocalDate.now(), "Vacinação anual", new BigDecimal("120.00")));

        System.out.println("\n--- 4) Registrando uma segunda consulta para o mesmo animal ---");
        consultaController.registrar(
                new Consulta(animal.getId(), LocalDate.now().minusMonths(2), "Consulta de rotina", new BigDecimal("80.00")));

        System.out.println("\n--- 5) Histórico de consultas do animal '" + animal.getNome() + "' ---");
        consultaController.listarPorAnimal(animal.getId());

        System.out.println("\n--- 6) Animais cadastrados do tutor '" + tutor.getNome() + "' ---");
        animalController.listarPorTutor(tutor.getId());

        System.out.println("\n--- 7) Demonstrando regra de negócio: consulta para animal NÃO cadastrado ---");
        try {
            consultaController.registrar(
                    new Consulta(999999, LocalDate.now(), "Consulta inválida", new BigDecimal("50.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n--- 8) Demonstrando regra de negócio: valor de consulta NEGATIVO ---");
        try {
            consultaController.registrar(
                    new Consulta(animal.getId(), LocalDate.now(), "Consulta com valor inválido", new BigDecimal("-10.00")));
        } catch (IllegalArgumentException e) {
            System.out.println("Falha esperada: " + e.getMessage());
        }

        System.out.println("\n=== FIM DA SIMULAÇÃO ===");
    }
}
