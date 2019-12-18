import console.Console;
import controller.BookingController;
import controller.FlightController;
import database_files.Database;

import java.io.IOException;
import java.text.ParseException;

public class Core {
    private final Console console;
    private final Database database;
    private final Menu menu;
    private final Parser parser;
    private final FlightController flightController;
    private final BookingController bookingController;

    public Core(Console console, Database database) throws IOException, ParseException {
        this.console = console;
        this.database = database;
        this.menu = new Menu();
        this.parser = new Parser();
        this.flightController = new FlightController(console);
        this.bookingController = new BookingController(console);
    }

    public void run() throws IOException, ParseException {
        bookingController.load();
        flightController.load();
        if (!database.isExisted()) {
            database.createInitialData();
        }
        boolean cont = true;
        while (cont) {
            console.printLn("\n" + menu.show());
            String line = console.readLn();
            Command user_input = parser.parse(line);
            switch (user_input) {
                case TIMETABLE_SHOW:
                    flightController.show();
                    break;
                case TIMETABLE_LINE_SHOW:
                    flightController.showLine();
                    break;
                case FLIGHT_SEARCH:
                    flightController.search();
                    break;
                case BOOKING_REMOVE:
                    bookingController.remove();
                    break;
                case MY_BOOKINGS_SHOW:
                    bookingController.show();
                    break;
                case EXIT:
                    console.printLn("We are always happy to host you. If you have further questions,\n" +
                            "do not hesitate to contact us -> info@onlinebookingtickets.com");
                    cont = false;
                    break;
                default:
                    console.printLn("Wrong item. Select 1-6 ");
                    break;
            }
        }
    }
}
