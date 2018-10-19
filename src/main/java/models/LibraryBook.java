package models;

public class LibraryBook{

    private boolean isElectronik;
    private String title;
    private String author;
    private int price;

    public LibraryBook(String title, String author, int price, boolean isElectronik){
        this.title = title;
        this.author = author;
        this.price = price;
        this.isElectronik = isElectronik;
    }

    public LibraryBook(String title, String author, int price){
        this.title = title;
        this.author = author;
        this.price = price;
        this.isElectronik = false;
    }

    public LibraryBook(){
    }

    public boolean isElectronik() {
        return isElectronik;
    }

    public void setElectronik(boolean electronik) {
        isElectronik = electronik;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

