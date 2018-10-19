package services;

import dao.BookDao;
import models.LibraryBook;

import java.awt.print.Book;

public class BookService {
    private BookDao bookDao = new BookDao();

    public BookService() {
    }

    public void findBookByAuthor(String author){
        bookDao.findByAuthor(author);
    }

    public void findBookByTitle(String title){
        bookDao.findByTitle(title);
    }

    public void saveBook(LibraryBook book) {
        bookDao.save(book);
    }

    public void findElectonikBooks(Boolean isElectr){
        bookDao.findElectonik(isElectr);
    }

    public void deleteBook(LibraryBook book){
        if (bookDao.delete(book)){
            System.out.format("Book %s was deleted successfully", book.toString());
        } else {
            System.out.format("Error: book %s was't found", book.toString());
        }

    }
}
