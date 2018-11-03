package models;

/**
 * Book entity
 * @author StephanovichYegor
 * @version 1.1
 */
public class LibraryBook{

    private boolean isElectronik;
    private String title;
    private String author;
    private int price;

    /**
     * Constrictor of Book entity with 4 params
     * @param title book title
     * @param author book author
     * @param price book price
     * @param isElectronik type of book(paper/electronic)
     */
    public LibraryBook(String title, String author, int price, boolean isElectronik){
        this.title = title;
        this.author = author;
        this.price = price;
        this.isElectronik = isElectronik;
    }

    /**
     * Constrictor of Book entity with 3 params(default not electronic)
     * @param title book title
     * @param author book author
     * @param price book price
     */
    public LibraryBook(String title, String author, int price){
        this.title = title;
        this.author = author;
        this.price = price;
        this.isElectronik = false;
    }

    /**
     * Empty constructor
     */
    public LibraryBook(){
    }

    /**
     * Is book electronic or paper
     * @return true if electronic, else false
     */
    public boolean isElectronik() {
        return isElectronik;
    }

    /**
     * Set type of book
     * @param electronik true if electronic, else false
     */
    public void setElectronik(boolean electronik) {
        isElectronik = electronik;
    }

    /**
     * Get item book title
     * @return book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set item book title
     * @param title book title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get item book author
     * @return item book author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set item book author
     * @param author item book author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get item book price
     * @return item book price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set item book price
     * @param price item book price
     */
    public void setPrice(int price) {
        this.price = price;
    }

}

