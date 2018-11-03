package dao;

import dao.bookdao.BookDao;
import dao.userdao.UserDao;

/**
 * Data access layer factory creates Dao of different entities
 * @author StephanovichYegor
 * @version 1.1
 */
public class DaoFactory {
    private static UserDao userDao = new UserDao();
    private static BookDao bookDao = new BookDao();

    /**
     * Empty constructor
     */
    private DaoFactory(){

    }

    /**
     * Get UserDao
     * @return UserDao
     */
    public static UserDao getUserDao(){
        return userDao;
    }

    /**
     * Get BookDao
     * @return BookDao
     */
    public static BookDao getBookDao(){
        return bookDao;
    }
}
