package dao.bookdao;

import models.LibraryBook;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import services.InsertAlreadyExistingRecordException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Communicate with book's table in database
 * @author StephanovichYegor
 * @version 1.1
 */
public class BookDao implements IBookDao {

    /**
     * Constructor connect driver of database
     */
    public BookDao(){
        try {
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e){
            System.err.println("Can't load JDBC-driver");
        }
    }

    /**
     * Save book data in database
     * @param book book entity
     * @return true if book saved successfully, else false
     */
    public boolean save(LibraryBook book) throws InsertAlreadyExistingRecordException{
        String insertSql = "INSERT INTO books (title, author, price, electronik) VALUES (?, ?, ?, ?)";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPrice());
            statement.setBoolean(4, book.isElectronik());
            result = (statement.executeUpdate() == 1);
        } catch (PSQLException e){
            if (e.getErrorCode() == 0 || e.getMessage().contains("duplicate key value violates unique constraint")){
                String message = "Insert Error: Book with such title already exists";
                throw new InsertAlreadyExistingRecordException(message, new PSQLState(e.getSQLState()));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete book from database by definite author and title
     * @param author book author
     * @param title book title
     * @return true if book deleted successfully, else false
     */
    public boolean delete(String author, String title){
        String insertSql = "DELETE FROM books WHERE author=? AND title=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, author);
            statement.setString(2, title);
            return statement.executeUpdate() == 1;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * select all books from catalog
     * @return list of books
     */
    public List<LibraryBook> getAllBooks(){
        String insertSql = "SELECT * FROM books ORDER BY title";
        List<LibraryBook>  bookList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(insertSql)){
            ResultSet st = statement.executeQuery();
            bookList =  getListFromResultSet(st);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * Using to find list of books by item title
     * @param title book title
     * @return list of books with item title
     */
    public List<LibraryBook> findByTitle(String title){
        String insertSql = "SELECT * FROM books WHERE title ILIKE ?";
        return getQuary(title, insertSql);
    }

    /**
     * Using to find list of books by item author
     * @param author book author
     * @return list of books with item author
     */
    public List<LibraryBook> findByAuthor(String author){
        String insertSql = "SELECT * FROM books WHERE author ILIKE ?";
        return getQuary(author, insertSql);
    }

    /**
     * Using to find list of books of definite type
     * @param isElectr true if you need electronic books, else false
     * @return list of books of item type
     */
    public List<LibraryBook> findElectonik(Boolean isElectr){
        String insertSql = "SELECT * FROM books WHERE electronik=?";
        List<LibraryBook>  bookList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){

            statement.setBoolean(1, isElectr);
            ResultSet resultSet = statement.executeQuery();
            bookList = getListFromResultSet(resultSet);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * Revert info from ResultSet to list of books
     * @param resultSet Set getting by sql query
     * @throws SQLException throws if sql query was fault
     * @return list of books
     */
    private List<LibraryBook> getListFromResultSet(ResultSet resultSet) throws SQLException {
        List<LibraryBook> bookList = new ArrayList<>();
        while (resultSet.next()){
            LibraryBook book = new LibraryBook();
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setPrice(resultSet.getInt("price"));
            book.setElectronik(resultSet.getBoolean("electronik"));
            bookList.add(book);
        }
        return bookList;
    }

    /**
     * Return the result of item query
     * @param fieldValue field value using in query
     * @param insertSql sql query performing from database
     * @return list of book which find by fieldValue
     */
    private List<LibraryBook> getQuary(String fieldValue, String insertSql){
        List<LibraryBook>  bookList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1,  '%' + fieldValue + '%');
            ResultSet resultSet = statement.executeQuery();
            bookList = getListFromResultSet(resultSet);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }
}
