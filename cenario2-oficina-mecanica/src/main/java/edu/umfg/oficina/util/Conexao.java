package edu.umfg.oficina.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por centralizar a configuração da conexão JDBC com o
 * banco de dados PostgreSQL.
 *
 * Ajuste URL, USUARIO e SENHA de acordo com o seu ambiente local antes de
 * executar a aplicação.
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/oficina_mecanica";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    private Conexao() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
