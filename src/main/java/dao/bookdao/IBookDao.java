package dao.bookdao;

import models.LibraryBook;
import services.InsertAlreadyExistingRecordException;

import java.util.List;

public interface IBookDao {
    String URL = "jdbc:postgresql://localhost:5432/postgres";
    String USERNAME = "postgres";
    String PASSWORD = "57765egor";

    List<LibraryBook> getAllBooks();
    boolean delete(String author, String title);
    boolean save(LibraryBook book) throws InsertAlreadyExistingRecordException;
    List<LibraryBook> findByTitle(String title);
    List<LibraryBook> findByAuthor(String author);
}
