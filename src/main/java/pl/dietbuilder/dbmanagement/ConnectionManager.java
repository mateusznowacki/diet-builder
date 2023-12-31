package pl.dietbuilder.dbmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance;
    private final Connection connection;
    private static final String URL = "jdbc:mariadb://mariadb1011.server639277.nazwa.pl:3306/server639277_diet";
    private static final String USER ="server639277_diet";// System.getenv("DB_USER");
    private static final String PASSWORD = "Admin123#";// System.getenv("DB_PASSWORD");

    private ConnectionManager() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Nie mozna znalezc sterownika JDBC", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Blad podczas nawiazywania polaczenia z baza danych", e);
        }
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.mariadb.jdbc.Driver");
                return DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Błąd podczas nawiązywania połączenia z bazą danych", e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Blad podczas zamykania polaczenia z baza danych", e);
        }
    }

}
