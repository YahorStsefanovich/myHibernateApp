package dao;

import models.LibraryBook;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class BookDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "57765egor";

    public BookDao(){
        try {
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e){
            System.err.println("Can't load JDBC-driver");
        }
    }

    public void save(LibraryBook book) {
        String insertSql = "INSERT INTO books (title, author, price, electronik) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPrice());
            statement.setBoolean(4, book.isElectronik());

            statement.executeUpdate();
        } catch (PSQLException e){
            if (e.getErrorCode() == 0 || e.getMessage().contains("duplicate key value violates unique constraint")){
                System.out.println("Insert Error: Book with such title already exists");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean delete(LibraryBook book){
        String insertSql = "DELETE FROM books WHERE author=? AND title=? AND price=? AND electronik=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getTitle());
            statement.setInt(3, book.getPrice());
            statement.setBoolean(4, book.isElectronik());
            return statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void printCatalog(){
        String insertSql = "SELECT * FROM books";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(insertSql)){
            ResultSet st = statement.executeQuery();
            while (st.next()){
                System.out.format("Title: %s, author: %s, price: %s, isElectornic: %b\n",
                        st.getString("title"),
                        st.getString("author"),
                        st.getString("price"),
                        st.getBoolean("electronik")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void findByTitle(String title){
        String insertSql = "SELECT * FROM books WHERE title=?";
        selectQuary(title, insertSql);
    }

    public void findByAuthor(String author){
        String insertSql = "SELECT * FROM books WHERE author=?";
        selectQuary(author, insertSql);
    }

    public void findElectonik(Boolean isElectr){
        String insertSql = "SELECT * FROM books WHERE electronik=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setBoolean(1, isElectr);
            ResultSet st = statement.executeQuery();
            while (st.next()){
                System.out.format("\nBOOKS %s :\n", isElectr? "ELECTRONIK" : "NOT ELECTRONIK");
                System.out.format("Title: %s, author: %s, price: %s",
                        st.getString("title"),
                        st.getString("author"),
                        st.getString("price")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void selectQuary(String fieldValue, String insertSql){
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSql)){
            statement.setString(1, fieldValue);
            ResultSet st = statement.executeQuery();
            while (st.next()){
                System.out.format("\nBOOKS WITH fieldValue '%s':\n", fieldValue);
                System.out.format("Title: %s, author: %s, price: %s, isElectornic: %b",
                        st.getString("title"),
                        st.getString("author"),
                        st.getString("price"),
                        st.getBoolean("electronik")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
