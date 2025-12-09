package app;

import app.gui.GUIApp;
import app.cli.CLIApp;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            GUIApp.lancerGUI();
        } else {
            CLIApp.run(args);
        }
    }
}
