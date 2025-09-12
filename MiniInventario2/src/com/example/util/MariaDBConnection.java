package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBConnection {
	// Datos de conexión
    private static final String URL = "jdbc:mariadb://localhost:3306/inventario";
    private static final String USER = "inventario";
    private static final String PASSWORD = "123456";
    
    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}