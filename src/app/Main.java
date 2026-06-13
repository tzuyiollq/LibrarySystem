package app;
import controller.LoginController;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame();
        new LoginController(frame);
    }
}
