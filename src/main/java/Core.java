import console.Console;
import controller.BookingController;
import controller.TimetableController;
import database_files.Database;
import io.Command;
import io.Parser;

import java.io.IOException;
import java.text.ParseException;

public class Core {
    private final Console console;
    private final Database database;
    private final Menu menu;
    private final Parser parser;
    private final BookingController bookingController;
    private final TimetableController timetableController;

    public Core(Console console, Database database) throws IOException, ParseException {
        this.console = console;
        this.database = database;
        this.menu = new Menu();
        this.parser = new Parser();
        this.bookingController = new BookingController();
        this.timetableController = new TimetableController();
    }

    public void run() throws IOException, ParseException {
        bookingController.load();
        timetableController.load();
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
                    timetableController.show();
                    break;
                case TIMETABLE_LINE_SHOW:
                    timetableController.showLine();
                    break;
                case FLIGHT_SEARCH:
                    timetableController.search();
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
