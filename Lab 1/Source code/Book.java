
public class Book {

    // ── Fields ──
    private String isbn;        // e.g. "978-0134685991"
    private String title;       // e.g. "Effective Java"
    private String author;      // e.g. "Joshua Bloch"
    private boolean checkedOut;  // true if currently borrowed

    // ── Constructor ──
    public Book(String isbn, String title,
                String author) {
        this.isbn       = isbn;
        this.title      = title;
        this.author     = author;
        this.checkedOut = false; // new books start available
    }

    // ── Getters ──
    public String  getIsbn()       { return isbn; }
    public String  getTitle()      { return title; }
    public String  getAuthor()     { return author; }
    public boolean isCheckedOut()   { return checkedOut; }

    // ── Checkout / Return ──
    public void checkOut()  { this.checkedOut = true; }
    public void returnBook() { this.checkedOut = false; }

    /**
     * Returns a human-readable availability status.
     * This logic belongs in the Model because it is
     * a business rule, not a display concern.
     */
    public String getStatus() {
        return checkedOut ? "Checked Out" : "Available";
    }
}