import Managers.UserManager;
import Services.InputReaderService;

public class App {
    public static void main(String[] args) throws Exception {
        initialize();
        UI.run();
        InputReaderService.end();
    }

    private static void initialize() {
        UserManager.generateData();
    }
}
