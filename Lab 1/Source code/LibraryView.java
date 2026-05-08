import java.util.List;
import java.util.Scanner;

/**
 * LibraryView.java - Handles ALL user interaction.
 * VIEW layer only.
 */
public class LibraryView {

    private final Scanner scanner = new Scanner(System.in);

    // ═══════════════════════════════════════════
    // OUTPUT METHODS (display to user)
    // ═══════════════════════════════════════════

    /** Displays the main menu and returns the choice. */
    public int showMenu() {
        System.out.println();
        System.out.println("============================");
        System.out.println("    LIBRARY BOOK MANAGER");
        System.out.println("============================");
        System.out.println("1. View all books");
        System.out.println("2. Search book by ISBN");
        System.out.println("3. Check out a book");
        System.out.println("4. View availability summary");
        System.out.println("5. Exit");
        System.out.println("6. Return ");
        System.out.println("7. Add new book ");
        System.out.println("8.Search Author ");
        System.out.println("9. View the checkout : ");
        System.out.print("Enter your choice: ");

        return readInt();
    }
    /** Displays a list of books in a formatted table. */
    public void showBookList(List<Book> books) {
        System.out.println();
        System.out.printf("%-18s %-28s %-20s %s%n",
                "ISBN", "Title", "Author", "Status");
        System.out.println("-".repeat(78));

        for (Book b : books) {
            System.out.printf("%-18s %-28s %-20s %s%n",
                    b.getIsbn(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getStatus());
        }
    }

    /** Displays details for a single book. */
    public void showBookDetails(Book book) {
        System.out.println();
        System.out.println("---Book Found---");
        System.out.println("ISBN:   " + book.getIsbn());
        System.out.println("Title:  " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Status: " + book.getStatus());
    }


    /** Displays the availability summary. */
    public void showAvailability(int available, int total) {
        System.out.printf("%nAvailability: %d of %d books available%n",
                available, total);
    }


    /** Displays an error or informational message. */
    public void showMessage(String message) {
        System.out.println(message);
    }
    // ═══════════════════════════════════════════
    // INPUT METHODS (collect from user)
    // ═══════════════════════════════════════════

    /** Prompts for and returns a book ISBN. */

    public String askIsbn() {
        System.out.print("Enter book ISBN: ");
        return scanner.nextLine().trim();
    }

    public String askReturnIsbn() {  // tAsk 2
        System.out.print("Enter ISBN to return: ");
        return scanner.nextLine().trim();
    }

    // new book show
    public String[] askNewBookDetails() {   // task 3
        System.out.println("--- Add New Book ---");

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();

        return new String[]{isbn, title, author};
    }


    // author name search
    public String askAuthorName() {  // task 4
        System.out.print("Enter author name: ");
        return scanner.nextLine().trim();
    }










    /** Safely reads an integer from the user. */
    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.next(); // discard bad input
            System.out.print("Invalid. Enter a number: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
}