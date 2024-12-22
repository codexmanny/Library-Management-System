package library.management.system;

class Book 
{
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private boolean available;

    public Book(int bookId, String title, String author, String isbn, boolean available) 
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
    }

    public int getBookId() 
    {
        return bookId;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getIsbn() 
    {
        return isbn;
    }

    public boolean isAvailable() 
    {
        return available;
    }

    public void setAvailable(boolean available) 
    {
        this.available = available;
    }

    @Override
    public String toString() 
    {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + available;
    }
}