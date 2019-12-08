import console.Console;
import console.SystemConsole;
import database_files.Database;

import java.io.IOException;
import java.text.ParseException;

public class App {

    public static void main(String[] args) throws IOException, ParseException {
        Console console = new SystemConsole();
        console.printLn("The system is getting ready. Please keep waiting..");
        Database database = new Database();
        Core app = new Core(console, database);
        app.run();
    }
}
