package app.model;

/**
 * Classe Mp3File
 * ----------------
 * Cette classe représente un fichier MP3 dans l'application.
 * Elle contient :
 * - le chemin d'accès au fichier sur le disque
 * - les métadonnées associées (titre, artiste, album, etc.)
 *
 * C'est une classe simple qui permet de manipuler les fichiers MP3
 * de manière structurée.
 */
public class Mp3File {

    // ---------------------------
    //       ATTRIBUTS
    // ---------------------------

    /** Chemin complet du fichier MP3 sur le disque (ex : "/musique/fichier.mp3") */
    private String cheminFichier;

    /** Métadonnées du fichier MP3 (titre, artiste, album, durée...) */
    private Metadata metadata;


    // ---------------------------
    //     CONSTRUCTEUR
    // ---------------------------

    /**
     * Constructeur principal.
     *
     * @param cheminFichier  chemin vers le fichier MP3
     * @param metadata       objet contenant les informations du morceau
     */
    public Mp3File(String cheminFichier, Metadata metadata) {
        this.cheminFichier = cheminFichier;
        this.metadata      = metadata;
    }


    // ---------------------------
    //       GETTERS
    // ---------------------------

    /** @return le chemin complet du fichier MP3 */
    public String getCheminFichier() {
        return cheminFichier;
    }

    /** @return les métadonnées associées au fichier */
    public Metadata getMetadata() {
        return metadata;
    }


    // ---------------------------
    //     MÉTHODE UTILE
    // ---------------------------

    /**
     * Retourne une représentation texte du fichier MP3.
     * Exemple : "Title (C:/music/track.mp3)"
     *
     * Très utile pour afficher dans une liste ou pour du debug.
     */
    @Override
    public String toString() {
        return metadata.getTitre() + " (" + cheminFichier + ")";
    }
}
