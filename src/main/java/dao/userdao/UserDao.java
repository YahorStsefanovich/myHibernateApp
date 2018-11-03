package dao.userdao;

import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import services.InsertAlreadyExistingRecordException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Communicate with user's table in database
 * @author StephanovichYegor
 * @version 1.1
 */
public class UserDao implements IUserDao {

    /**
     * Constructor connect driver of database
     */
    public UserDao(){
        try {
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e){
            System.err.println("Can't load JDBC-driver");
        }
    }

    /**
     * Delete user's data from database
     * @param login user's Email
     * @return If user successfully deleted from database true, else false
     */
    public boolean delete(String login){
        String insertSql = "DELETE FROM users WHERE login=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, login);
            return statement.executeUpdate() == 1;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Save user's data in database
     * @param user  User entity
     * @return If user successfully saved in database true, else false
     */
    public boolean save(User user)  throws InsertAlreadyExistingRecordException {
        String insertSql = "INSERT INTO users (login, password, admin) VALUES (?, ?, ?)";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.isAdmin());

            result = (statement.executeUpdate() == 1);
        } catch (PSQLException e){
            if (e.getErrorCode() == 0 ||
                    e.getMessage().contains("duplicate key value violates unique constraint")){
                String message = "Insert Error: User with such login already exists";
                throw new InsertAlreadyExistingRecordException(message, new PSQLState(e.getSQLState()));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Check if user with item login and password exists in database
     * @param login user's Email
     * @param password user's password
     * @return User info if item login and password exists in database, else null
     */
    public User authorize(String login, String password){
        User user = null;
        String insertSql = "SELECT login, password, admin  FROM users WHERE login=? AND password=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, login);
            statement.setString(2, DigestUtils.md5Hex(password));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new User(
                        resultSet.getString("login"),
                        password,
                        resultSet.getBoolean("admin")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Get list of all register users in database
     * @return list of all register users in database
     */
    public List<User> getAllUsers(){
        List<User> loginList = new ArrayList<>();
        String insertSql = "SELECT * FROM users";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String login =  resultSet.getString("login");
                String password =  resultSet.getString("password");
                Boolean isAdmin =  resultSet.getBoolean("admin");

                User user = new User(login, password, isAdmin);
                loginList.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return loginList;
    }
}
