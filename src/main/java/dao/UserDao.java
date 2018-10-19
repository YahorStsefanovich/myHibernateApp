package dao;

import models.LibraryBook;
import models.User;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class UserDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "57765egor";

    public UserDao(){
        try {
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e){
            System.err.println("Can't load JDBC-driver");
        }
    }

    public boolean delete(String login){
        String insertSql = "DELETE FROM users WHERE login=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, login);
            return statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void save(User user) {
        String insertSql = "INSERT INTO users (login, password, admin) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.isAdmin());

            statement.executeUpdate();
        } catch (PSQLException e){
            if (e.getErrorCode() == 0 ||
                    e.getMessage().contains("duplicate key value violates unique constraint")){
                System.out.println("Insert Error: User with login already exists");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
