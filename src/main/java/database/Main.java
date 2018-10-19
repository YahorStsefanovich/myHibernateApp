package database;

import dao.BookDao;
import dao.UserDao;
import models.LibraryBook;
import models.User;
import org.postgresql.util.PSQLException;
import services.BookService;
import services.UserService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.sql.SQLException;

public class Main {

    public static void main(String [] args){
        BookService bookService = new BookService();
        LibraryBook book = new LibraryBook("Вечера на хуторе близ диканьки", "Н. Гоголь", 20, true);
        bookService.saveBook(book);
       // bookService.printCatalog();
        bookService.findBookByAuthor("Вечера на хуторе близ диканьки");
        bookService.findBookByAuthor("Н. Гоголь");
        bookService.findElectonikBooks(true);
        bookService.findElectonikBooks(false);

        UserService userService = new UserService();
        String login = "egor";
        userService.saveUser(new User(login, "1111", false));
        bookService.deleteBook(book);
    }


    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}


