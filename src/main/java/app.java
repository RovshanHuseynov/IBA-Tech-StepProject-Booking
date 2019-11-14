import Console.SystemConsole;


import java.io.IOException;
import java.text.ParseException;

public class app {

    public static void main(String[] args) throws IOException, ParseException {
        SystemConsole console = new SystemConsole();
        Database database = new Database();
        Core app = new Core(console, database);
        app.run();
    }}
