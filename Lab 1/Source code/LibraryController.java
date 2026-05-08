import java.util.List;

/**
 * LibraryController.java - The CONTROLLER layer.
 * Connects Model and View.
 */
public class LibraryController {

    private final Library model;
    private final LibraryView view;

    public LibraryController(Library model, LibraryView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        boolean running = true;

        while (running) {
            int choice = view.showMenu();

            switch (choice) {
                case 1 -> handleViewAll();
                case 2 -> handleSearchByIsbn();
                case 3 -> handleCheckOut();
                case 4 -> handleAvailability();
                case 5 -> {
                    view.showMessage("Goodbye!");
                    running = false;
                }
                case 6 -> handleReturn();
                case 7 -> handleAddBook();
                case 8 -> handleSearchByAuthor();
                case 9 -> handleViewCheckedOut();
                default -> view.showMessage("Invalid choice. Try again.");
            }
        }
    }

    private void handleViewAll() {
        view.showBookList(model.getAllBooks());
    }

    private void handleSearchByIsbn() {
        String isbn = view.askIsbn();
        Book book = model.findByIsbn(isbn);

        if (book == null) {
            view.showMessage("No book found with ISBN: " + isbn);
            return;
        }

        view.showBookDetails(book);
    }

    private void handleCheckOut() {
        String isbn = view.askIsbn();
        Book book = model.findByIsbn(isbn);

        if (book == null) {
            view.showMessage("No book found with ISBN: " + isbn);
            return;
        }

        if (book.isCheckedOut()) {
            view.showMessage("Book already checked out.");
            return;
        }

        book.checkOut();
        view.showMessage("Checked out: " + book.getTitle());
    }

    private void handleAvailability() {
        view.showAvailability(
                model.getAvailableCount(),
                model.getTotalCount()
        );
    }



    private void handleReturn() {    // TASK 2
        String isbn = view.askReturnIsbn();
        Book book = model.findByIsbn(isbn);

        if (book == null) {
            view.showMessage("No book found with ISBN: " + isbn);
            return;
        }

        if (!book.isCheckedOut()) {
            view.showMessage("Book is already available.");
            return;
        }

        book.returnBook();
        view.showMessage("Returned: " + book.getTitle());
    }

////////////////////////////////////////////////////////
    private void handleAddBook() {      // TASK 3
        String[] details = view.askNewBookDetails();

        if (details == null || details.length < 3) {
            view.showMessage("Invalid book details.");
            return;
        }

        String isbn = details[0];
        String title = details[1];
        String author = details[2];

        if (model.findByIsbn(isbn) != null) {  // search for book
            view.showMessage("Book already exists with ISBN: " + isbn);
            return;
        }

        model.addBook(new Book(isbn, title, author));
        view.showMessage("Book added: " + title);
    }



    private void handleSearchByAuthor() { //    TASK 4:
        String author = view.askAuthorName();
        List<Book> results = model.getBooksByAuthor(author);

        if (results == null || results.isEmpty()) {

            view.showMessage("No books found for:"+author);
            return;
        }
        view.showBookList(results);
    }

    private void handleViewCheckedOut() {   //  Task  5
        List<Book> books = model.getCheckedOutBooks();

        if (books == null || books.isEmpty()) {
            view.showMessage("No books are currently checked out.");
            return;
        }

        view.showBookList(books);
    }
}