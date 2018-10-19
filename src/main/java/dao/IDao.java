package dao;

import models.LibraryBook;
import models.User;

public interface IDao {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "57765egor";

    void save(LibraryBook book);
    void save(User user);
}
