package app.cli;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import app.service.FolderExplorer;
import app.service.MetadataReader;
import app.service.PlaylistBuilder;
import app.service.XSPFExporter;
import app.service.M3U8Exporter;
import app.service.JSONExporter;

/**
 * Classe CLIApp
 * --------------
 * Ce programme permet d'utiliser l'application en LIGNE DE COMMANDE.
 *
 * Commandes principales :
 *   -h              → Affiche l'aide
 *   -d <dossier>    → Analyse un dossier (récursif) et génère une playlist
 *   -f <fichier>    → Analyse un fichier MP3 et affiche les métadonnées
 *
 * Options export :
 *   --xspf -o <f>   → Export en XSPF
 *   --m3u8 -o <f>   → Export en M3U8
 *   --json -o <f>   → Export en JSON
 *
 * Tout est simple, sans bibliothèques externes.
 */
public class CLIApp {

    /**
     * Point d'entrée du programme CLI.
     * On analyse simplement l'argument principal.
     */
    public static void run(String[] args) {

        // Aucun argument → message d'erreur
        if (args.length == 0) {
            erreur("Aucun argument fourni. Utilisez -h pour afficher l’aide.");
            return;
        }

        String option = args[0];

        // On choisit l'action principale
        switch (option) {

            case "-h":
                afficherAide();
                break;

            case "-d":
                analyserDossier(args);
                break;

            case "-f":
                analyserFichier(args);
                break;

            default:
                erreur("Option inconnue : " + option);
                System.out.println("Utilisez -h pour afficher l'aide.");
        }
    }


    // ============================================================
    //                    UTILITAIRE : MESSAGE ERREUR
    // ============================================================

    /**
     * Affiche un message d'erreur uniforme.
     */
    private static void erreur(String msg) {
        System.out.println("[ERREUR] " + msg);
    }


    // ============================================================
    //                          AIDE
    // ============================================================

    /**
     * Affiche l'aide détaillée.
     */
    private static void afficherAide() {

        System.out.println("===============================================");
        System.out.println("         MP3 MANAGER - AIDE COMPLETE");
        System.out.println("===============================================\n");

        System.out.println("UTILISATION GENERALE :");
        System.out.println("  java -jar cli.jar <commande> [options]\n");

        System.out.println("COMMANDES PRINCIPALES :");
        System.out.println("  -h                  Affiche cette aide.");
        System.out.println("  -d <dossier>        Explore dossier et génère une playlist.");
        System.out.println("  -f <fichier.mp3>    Analyse un fichier MP3 et affiche ses métadonnées.\n");

        System.out.println("OPTIONS D'EXPORT :");
        System.out.println("  --xspf -o <fichier>   Export XSPF");
        System.out.println("  --m3u8 -o <fichier>   Export M3U8");
        System.out.println("  --json -o <fichier>   Export JSON (JSPF)\n");

        System.out.println("AUTEURS :");
        System.out.println("  Projet réalisé par : Seddik MESSAOUDI & Said IBAZATENE");
        System.out.println("===============================================");
    }


    // ============================================================
    //                 ANALYSE D'UN DOSSIER COMPLET
    // ============================================================

    /**
     * Analyse un dossier, génère une playlist et optionnellement l'exporte.
     */
    private static void analyserDossier(String[] args) {

        // Vérification : dossier manquant
        if (args.length < 2) {
            erreur("Dossier non indiqué. Exemple : -d C:/music");
            return;
        }

        String cheminDossier = args[1];

        // 1) Génération de la playlist via le PlaylistBuilder
        Playlist playlist = PlaylistBuilder.fromDirectory(cheminDossier);

        // Affichage du contenu généré
        System.out.println("\n=== Playlist générée ===");
        System.out.println(playlist);

        // ------------------------------------------------------------
        // 2) Lecture des options (--xspf, --m3u8, --json, -o fichier)
        // ------------------------------------------------------------
        boolean xspf = false, m3u8 = false, json = false;
        String fichierSortie = null;

        for (int i = 2; i < args.length; i++) {

            switch (args[i]) {
                case "--xspf": xspf = true; break;
                case "--m3u8": m3u8 = true; break;
                case "--json": json = true; break;

                case "-o":
                    if (i + 1 < args.length) {
                        fichierSortie = args[i + 1];
                    }
                    break;
            }
        }

        // ------------------------------------------------------------
        // 3) Vérifications de cohérence des options
        // ------------------------------------------------------------

        // Plusieurs formats → erreur
        int count = (xspf ? 1 : 0) + (m3u8 ? 1 : 0) + (json ? 1 : 0);
        if (count > 1) {
            erreur("Vous ne pouvez choisir qu’un seul format (--xspf, --m3u8, --json)");
            return;
        }

        // Format choisi mais pas de fichier -o
        if (count == 1 && fichierSortie == null) {
            erreur("L’option -o <fichier> est obligatoire pour exporter.");
            return;
        }

        // Aucun export demandé → on s'arrête là
        if (count == 0) {
            System.out.println("Aucun export demandé.");
            return;
        }

        // ------------------------------------------------------------
        // 4) EXPORT DU FICHIER
        // ------------------------------------------------------------
        boolean ok = false;

        if (xspf) ok = XSPFExporter.exporter(playlist, fichierSortie);
        if (m3u8) ok = M3U8Exporter.exporter(playlist, fichierSortie);
        if (json) ok = JSONExporter.exporter(playlist, fichierSortie);

        System.out.println(ok ? "Export réussi !" : "Erreur lors de l’export.");
    }


    // ============================================================
    //               ANALYSE D’UN FICHIER MP3 UNIQUE
    // ============================================================

    /**
     * Analyse un seul fichier MP3 et affiche ses métadonnées.
     */
    private static void analyserFichier(String[] args) {

        if (args.length < 2) {
            erreur("Fichier non indiqué. Exemple : -f chanson.mp3");
            return;
        }

        String chemin = args[1];

        // Lecture du fichier via MetadataReader
        Mp3File mp3 = MetadataReader.lireFichier(chemin);

        if (mp3 == null) {
            erreur("Impossible de lire le fichier : " + chemin);
            return;
        }

        Metadata m = mp3.getMetadata();

        // Affichage propre des métadonnées
        System.out.println("\n=== Métadonnées ===");
        System.out.println("Titre   : " + m.getTitre());
        System.out.println("Artiste : " + m.getArtiste());
        System.out.println("Album   : " + m.getAlbum());
        System.out.println("Durée   : " + m.getDuree() + " sec");
        System.out.println("Fichier : " + mp3.getCheminFichier());
    }
}
