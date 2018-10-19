package services;

import dao.UserDao;
import models.User;

public class UserService {

    private UserDao userDao = new UserDao();

    public UserService() {
    }

    public void saveUser(User user) {
        userDao.save(user);
    }
    public void deleteUser(String login){
        if (userDao.delete(login)) {
            System.out.format("User %s was deleted successfully", login);
        }else {
            System.out.format("Error: user %s wasn't deleted", login);
        }

    }
}
