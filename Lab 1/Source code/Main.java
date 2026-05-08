/**
 * Main.java - Entry point of the application.
 *
 * This class wires the MVC components together:
 *   1. Creates the Model  (data layer)
 *   2. Creates the View   (presentation layer)
 *   3. Creates the Controller (logic layer),
 *      passing it references to Model and View
 *   4. Starts the Controller
 *
 * Notice: Main does NOT contain any business logic.
 * It only performs dependency injection.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Create the Model (data + business rules)
        Library model = new Library();

        // 2. Create the View (user interface)
        LibraryView view = new LibraryView();

        // 3. Create the Controller, inject dependencies
        LibraryController controller =
                new LibraryController(model, view);

        // 4. Start the application loop
        controller.run();
    }
}