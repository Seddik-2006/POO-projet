package app;

import app.gui.GUIApp;
import app.cli.CLIApp;

/**
 * Classe Main
 * -----------
 * Point d'entrée du programme.
 *
 * Ce projet offre DEUX modes d'exécution :
 *   1) Sans arguments → lancement de l'interface graphique (GUI)
 *   2) Avec arguments → lancement du mode console (CLI)
 *
 * Cette classe détecte simplement si des arguments ont été fournis,
 * et redirige vers la bonne partie du programme.
 *
 * Le rôle de cette classe est volontairement minimal :
 * elle ne contient aucune logique métier.
 */
public class Main {

    public static void main(String[] args) {

        // Aucun argument → on lance le mode GUI
        if (args.length == 0) {
            GUIApp.lancerGUI();
        }

        // Sinon → mode ligne de commande
        else {
            CLIApp.run(args);
        }
    }
}
