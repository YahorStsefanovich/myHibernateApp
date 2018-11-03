package database;

import dao.DaoFactory;
import dao.bookdao.BookDao;
import dao.userdao.UserDao;
import services.UserService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Main {

    public static void main(String [] args){
        UserService userService = new UserService();
        while (true){
            userService.register();
        }
       // UserDao userDao = DaoFactory.getUserDao();
      //  userDao.delete("jagir-maloi@ramvler.ru");
    }
}


