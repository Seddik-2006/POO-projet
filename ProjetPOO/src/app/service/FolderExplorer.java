package app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe FolderExplorer
 * ----------------------
 * Cette classe permet de rechercher tous les fichiers .mp3 dans :
 *  - un dossier donné
 *  - tous ses sous-dossiers (récursivement)
 *
 * L'objectif est de fournir une liste de chemins absolus vers les fichiers MP3.
 * Elle est utilisée pour alimenter les objets Mp3File plus tard.
 */
public class FolderExplorer {

    /**
     * Explore un dossier (et sous-dossiers) et renvoie la liste
     * de tous les fichiers MP3 trouvés.
     *
     * @param cheminDossier chemin du dossier à explorer
     * @return liste des chemins complets des fichiers MP3
     */
    public static List<String> explorer(String cheminDossier) {

        // La liste qui va contenir tous les chemins MP3 trouvés
        List<String> mp3Files = new ArrayList<>();

        // Création d'un objet File représentant le dossier de départ
        File dossier = new File(cheminDossier);

        // Vérification simple : existe-t-il ? est-ce un dossier ?
        if (!dossier.exists() || !dossier.isDirectory()) {
            System.out.println("Erreur : dossier inexistant ou invalide.");
            return mp3Files;   // On retourne une liste vide (pas d'exception)
        }

        // Appel de la fonction récursive
        explorerRecursif(dossier, mp3Files);

        return mp3Files;
    }


    /**
     * Méthode récursive qui explore :
     *  - si c'est un dossier → explorer tous les fichiers qu'il contient
     *  - si c'est un fichier → vérifier si c'est un .mp3
     *
     * @param fichier  fichier ou dossier à analyser
     * @param resultat liste dans laquelle on ajoute les chemins des fichiers MP3
     */
    private static void explorerRecursif(File fichier, List<String> resultat) {

        // CAS 1 : c'est un dossier → on explore son contenu
        if (fichier.isDirectory()) {

            File[] sousFichiers = fichier.listFiles();

            // Vérification (parfois listFiles() peut renvoyer null)
            if (sousFichiers != null) {
                for (File f : sousFichiers) {
                    explorerRecursif(f, resultat); // appel récursif
                }
            }
        }

        // CAS 2 : c'est un fichier → on vérifie s'il finit par ".mp3"
        else {
            String nom = fichier.getName().toLowerCase();

            if (nom.endsWith(".mp3")) {
                resultat.add(fichier.getAbsolutePath());
            }
        }
    }
}
