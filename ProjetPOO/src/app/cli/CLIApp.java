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

public class CLIApp {

    public static void run(String[] args) {

        if (args.length == 0) {
            erreur("Aucun argument fourni. Utilisez -h pour afficher l’aide.");
            return;
        }

        String option = args[0];

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

    // ===========================
    // MESSAGE ERREUR UNIFORME
    // ===========================

    private static void erreur(String msg) {
        System.out.println("[ERREUR] " + msg);
    }

    // ===========================
    // AIDE
    // ===========================

    private static void afficherAide() {
        System.out.println("===============================================");
        System.out.println("         MP3 MANAGER - AIDE COMPLETE");
        System.out.println("===============================================");

        System.out.println("\nUTILISATION GENERALE :");
        System.out.println("  java -jar cli.jar <commande> [options]\n");

        System.out.println("COMMANDES PRINCIPALES :");
        System.out.println("  -h                  Affiche cette aide.");
        System.out.println("  -d <dossier>        Explore dossier et génère une playlist.");
        System.out.println("  -f <fichier.mp3>    Analyse un fichier MP3 et affiche ses métadonnées.");

        System.out.println("\nOPTIONS D'EXPORT :");
        System.out.println("  --xspf -o <fichier>   Export XSPF");
        System.out.println("  --m3u8 -o <fichier>   Export M3U8");
        System.out.println("  --json -o <fichier>   Export JSON (JSPF)");

        System.out.println("\nAUTEURS :");
        System.out.println("  Projet réalisé par : Seddik MESSAOUDI & Said IBAZATENE");
        System.out.println("===============================================");
    }


    // ===========================
    // ANALYSER DOSSIER
    // ===========================

    private static void analyserDossier(String[] args) {

        if (args.length < 2) {
            erreur("Dossier non indiqué. Exemple : -d C:/music");
            return;
        }

        String cheminDossier = args[1];
        Playlist playlist = PlaylistBuilder.fromDirectory(cheminDossier);

        System.out.println("\n=== Playlist générée ===");
        System.out.println(playlist);

        // Lire les options
        boolean xspf = false;
        boolean m3u8 = false;
        boolean json = false;
        String fichierSortie = null;

        for (int i = 2; i < args.length; i++) {
            switch (args[i]) {
                case "--xspf": xspf = true; break;
                case "--m3u8": m3u8 = true; break;
                case "--json": json = true; break;
                case "-o":
                    if (i + 1 < args.length) fichierSortie = args[i + 1];
                    break;
            }
        }

        // Plusieurs formats = erreur
        if ((xspf ? 1 : 0) + (m3u8 ? 1 : 0) + (json ? 1 : 0) > 1) {
            erreur("Vous ne pouvez choisir qu’un seul format (--xspf, --m3u8, --json)");
            return;
        }

        // Export demandé mais pas de fichier
        if ((xspf || m3u8 || json) && fichierSortie == null) {
            erreur("L’option -o <fichier> est obligatoire pour exporter.");
            return;
        }

        // Aucun export demandé
        if (!xspf && !m3u8 && !json) {
            System.out.println("Aucun export demandé.");
            return;
        }

        // EXPORT
        boolean ok = false;

        if (xspf) ok = XSPFExporter.exporter(playlist, fichierSortie);
        if (m3u8) ok = M3U8Exporter.exporter(playlist, fichierSortie);
        if (json) ok = JSONExporter.exporter(playlist, fichierSortie);

        System.out.println(ok ? "Export réussi !" : "Erreur lors de l’export.");
    }


    // ===========================
    // ANALYSER FICHIER MP3
    // ===========================

    private static void analyserFichier(String[] args) {

        if (args.length < 2) {
            erreur("Fichier non indiqué. Exemple : -f chanson.mp3");
            return;
        }

        String chemin = args[1];

        Mp3File mp3 = MetadataReader.lireFichier(chemin);

        if (mp3 == null) {
            erreur("Impossible de lire le fichier : " + chemin);
            return;
        }

        Metadata m = mp3.getMetadata();

        System.out.println("\n=== Métadonnées ===");
        System.out.println("Titre   : " + m.getTitre());
        System.out.println("Artiste : " + m.getArtiste());
        System.out.println("Album   : " + m.getAlbum());
        System.out.println("Durée   : " + m.getDuree() + " sec");
        System.out.println("Fichier : " + mp3.getCheminFichier());
    }
}
