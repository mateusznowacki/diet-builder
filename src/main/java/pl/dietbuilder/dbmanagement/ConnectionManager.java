package pl.dietbuilder.dbmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance;
    private final Connection connection;
    private static final String URL = "jdbc:mariadb://mariadb1011.server639277.nazwa.pl";
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

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
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        System.out.println("Zamykam polaczenie z baza danych");
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
