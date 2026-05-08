import java.util.ArrayList;
import java.util.List;

/**
 * Library.java - Manages a collection of Books.
 * This is the main MODEL class. It stores data and
 * provides methods to query and modify it.
 * It has NO knowledge of how data is displayed.
 */
public class Library {

    // The list that holds all book records
    private final List<Book> books = new ArrayList<>();

    // ── Constructor: pre-load some sample data ──
    public Library() {
        books.add(new Book("978-0134685991",
                "Effective Java", "Joshua Bloch"));
        books.add(new Book("978-0596009205",
                "Head First Design Patterns",
                "Eric Freeman"));
        books.add(new Book("978-0132350884",
                "Clean Code", "Robert C. Martin"));
        books.add(new Book("978-0201633610",
                "Design Patterns", "Gang of Four"));

        books.add(new Book("978-0596009220", "Atomic Habits", "Paolinho"));  // Task 1
        books.add(new Book("978-0596009234", "Baccha voyonkor", "Md.Zafor Iqbal"));
        books.add(new Book("978-0596009456", "Harry Potter", "J.K.Rowling"));
    }

    /**
     * Returns a copy of the full book list.
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Searches for a book by ISBN.
     * Returns null if not found.
     */
    public Book findByIsbn(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Adds a new book to the library.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Counts available books.
     */
    public int getAvailableCount() {
        int count = 0;
        for (Book b : books) {
            if (!b.isCheckedOut()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns total number of books.
     */
    public int getTotalCount() {
        return books.size();
    }



    public List<Book> getBooksByAuthor(String author) {   // task 4
        List<Book> result = new ArrayList<>();
        String query = author.toLowerCase();

        for (Book b : books) {
            if (b.getAuthor().toLowerCase().contains(query)) {
                result.add(b);
            }
        }
        return result;
    }


    public List<Book> getCheckedOutBooks() {   //task 5
        List<Book> result = new ArrayList<>();

        for (Book b : books) {
            if (b.isCheckedOut()) {
                result.add(b);
            }
        }
        return result;
    }
}