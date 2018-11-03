package services;

import dao.DaoFactory;
import dao.bookdao.BookDao;
import dao.userdao.UserDao;
import emailcontroller.ssl.EmailSender;
import models.LibraryBook;
import models.User;
import reader.ConsoleReader;

import java.util.List;

/**
 * Using to control(change, modify, add, remove values) book catalog
 * @author StephanovichYegor
 * @version 1.1
 */
public class BookService {
    private BookDao bookDao = DaoFactory.getBookDao();
    private UserDao userDao = DaoFactory.getUserDao();
    private EmailSender sslSender =
            new EmailSender(MyConfig.getMyEmail(), MyConfig.getMyEmailPassword());

    /**
     * Empty constructor
     */
    public BookService() {
    }

    /**
     * Print list of values by inputting author of book
     */
    void findBookByAuthor(){
        System.out.print("Input author: ");
        ConsoleReader.getString();
        String author = ConsoleReader.getString();
        printList(bookDao.findByAuthor(author));
    }

    /**
     * Print list of values by inputting title of book
     */
    void findBookByTitle(){
        System.out.print("Input title: ");
        ConsoleReader.getString();
        String  title = ConsoleReader.getString();
        printList(bookDao.findByTitle(title));
    }

    /**
     * Using to add new book to catalog and sent letter to all user with info about new book
     */
    void saveBook() {
        System.out.print("Input title: ");
        ConsoleReader.getString();
        String title = ConsoleReader.getString();
        System.out.print("Input author: ");
        String author = ConsoleReader.getString();
        System.out.print("Input price: ");
        int price = ConsoleReader.getInteger(1000);
        System.out.print("Is electonik: ");
        boolean isElectronik = ConsoleReader.getInteger(1) % 2 != 0;

        LibraryBook book = new LibraryBook(title, author, price, isElectronik);

        try{
            if(bookDao.save(book)){
                System.out.format("Book \"%s\"( %s) was saved successfully\n", book.getTitle(), book.getAuthor());
                String subject = "New book was add in catalog";
                String text = "Book \"" + book.getTitle() + "\" (" + book.getAuthor()+ ") was add to catalog.";
                for (User user : userDao.getAllUsers()){
                    if (!user.isAdmin()){
                        sslSender.send(subject, text,
                                "yegorstsephanovich@gmail.com", user.getLogin());
                        System.out.format("Message sent to %s", user.getLogin());
                    }
                }
            } else {
                System.out.format("Error: book '%s'(%s) was't save",  book.getTitle(), book.getAuthor());
            }
        }
        catch (InsertAlreadyExistingRecordException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Print list of books according to isElectr value
     * @param isElectr if true print all electronic books in catalog, else print all paper books
     */
    void findElectonikBooks(Boolean isElectr){
        printList(bookDao.findElectonik(isElectr));
    }

    /**
     * Removed book from catalog
     */
    void deleteBook(){
        System.out.print("Input title: ");
        ConsoleReader.getString();
        String title = ConsoleReader.getString();
        System.out.print("Input author: ");
        String author = ConsoleReader.getString();
        if (bookDao.delete(author, title)){
            System.out.format("Book %s was deleted successfully\n", title);
        } else {
            System.out.format("Error: book %s was't found\n", title);
        }

    }

    /**
     * Print item list
     * @param list list of books
     */
    private void printList(List<LibraryBook> list){
        for (LibraryBook book : list){
            System.out.format("Title: %20s, author: %15s, price: %4s, isElectornic: %6b\n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.isElectronik()
            );
        }
    }

    /**
     * Print list of all books in catalog
     */
    void printCatalog(){
        printList(bookDao.getAllBooks());
    }
}
