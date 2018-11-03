package services;

import dao.DaoFactory;
import dao.userdao.UserDao;
import models.User;
import reader.ConsoleReader;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Using for log in, register and choosing options of users and admins
 * @author StephanovichYegor
 * @version 1.1
 */
public class UserService {

    private UserDao userDao = DaoFactory.getUserDao();
    private BookService bookService = new BookService();
    private User user;

    /**
     * Empty constructor
     */
    public UserService() {
        user = null;
    }

    /**
     * Using to log in/register and authorize
     */
    public void register(){
        System.out.println("Do you have account?(0 - 'No', 1 - 'Yes')");
        boolean hasAccount = (ConsoleReader.getInteger(1) == 1);

        ConsoleReader.getString();
        System.out.print("Input login: ");
        String login = ConsoleReader.getString();
        System.out.print("Input password: ");
        String password = ConsoleReader.getString();

        if (!hasAccount){
            User user = new User(login, password, false);
            if (!isValidEmailAddress(login) || !saveUser(user)){
                return;
            }
        }

        authorize(login, password);
    }

    /**
     * Using for user's authorization
     * @param login user's Email
     * @param password user's account password
     */
    private void authorize(String login, String password){
        user = userDao.authorize(login, password);
        if (user == null){
            System.out.println("Wrong login or password!");
        } else{
            showOpportunities(user.isAdmin());
        }
    }

    /**
     * Using for showing available options acorrding to user or admin log in
     * @param isAdmin true if admin, false if not
     */
    private void showOpportunities(boolean isAdmin){
        while(true){
            if (isAdmin){

                System.out.println("\nChoose option: ");
                System.out.println("0. Exit");
                System.out.println("1. Modify book");
                System.out.println("2. Add book");
                System.out.println("3. Delete book");
                System.out.println("4. Show catalog");


                int choosedOption = ConsoleReader.getInteger(4);

                switch (choosedOption){
                    case 0 : System.exit(0);
                        break;
                    case 1 :
                        break;
                    case 2 : bookService.saveBook();
                        break;
                    case 3 : bookService.deleteBook();
                        break;
                    case 4 : bookService.printCatalog();
                        break;
                }
            }else {
                System.out.println("Choose option: ");
                System.out.println("0. Exit");
                System.out.println("1. Find book by title");
                System.out.println("2. Find book by author");
                System.out.println("3. Find electronik books");
                System.out.println("4. Find paper books");
                System.out.println("5. Show catalog");

                int choosedOption = ConsoleReader.getInteger(5);

                switch (choosedOption) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        bookService.findBookByTitle();
                        break;
                    case 2:
                        bookService.findBookByAuthor();
                        break;
                    case 3:
                        bookService.findElectonikBooks(true);
                        break;
                    case 4:
                        bookService.findElectonikBooks(false);
                        break;
                    case 5:
                        bookService.printCatalog();
                        break;
                }
            }

        }
    }

    /**
     * Using to register new user
     * @param user user entity(, password, isAdmin)
     */
    private boolean saveUser(User user) {
        boolean result = false;
        try{
            result = userDao.save(user);
            if (result) {
                System.out.format("User %s was saved successfully", user.getLogin());
            }else {
                System.out.format("Error: user %s wasn't saved", user.getLogin());
            }
        } catch (InsertAlreadyExistingRecordException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Using to unregister user
     * @param login user Email
     */
    public void deleteUser(String login){
        if (userDao.delete(login)) {
            System.out.format("User %s was deleted successfully", login);
        }else {
            System.out.format("Error: user %s wasn't deleted", login);
        }

    }

    /**
     * Check if user's Email is available
     * @param email user's Email
     * @return true if email available, else false
     */
    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            System.out.println("Check Email");
        }
        return result;
    }
}
