package dao.userdao;

import models.User;
import services.InsertAlreadyExistingRecordException;

import java.util.List;

public interface IUserDao {
    String URL = "jdbc:postgresql://localhost:5432/postgres";
    String USERNAME = "postgres";
    String PASSWORD = "57765egor";

    boolean delete(String login);
    boolean save(User user) throws InsertAlreadyExistingRecordException;
    User authorize(String login, String password);
    List<User> getAllUsers();
}
