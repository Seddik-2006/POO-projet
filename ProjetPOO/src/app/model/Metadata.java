package app.model;

/**
 * Classe Metadata
 * ----------------
 * Cette classe représente les métadonnées d'un fichier audio :
 * - titre de la musique
 * - artiste
 * - album
 * - durée en secondes
 *
 * C'est une classe simple (un "bean") utilisée pour stocker 
 * des informations, sans logique complexe.
 */
public class Metadata {

    // ---------------------------
    //       ATTRIBUTS
    // ---------------------------

    /** Titre de la musique */
    private String titre;

    /** Nom de l'artiste */
    private String artiste;

    /** Nom de l'album */
    private String album;

    /** Durée totale du morceau (en secondes) */
    private int duree;


    // ---------------------------
    //     CONSTRUCTEUR
    // ---------------------------

    /**
     * Constructeur principal.
     *
     * @param titre  le titre du morceau
     * @param artiste l'artiste du morceau
     * @param album  l'album du morceau
     * @param duree  la durée en secondes
     */
    public Metadata(String titre, String artiste, String album, int duree) {
        this.titre   = titre;
        this.artiste = artiste;
        this.album   = album;
        this.duree   = duree;
    }


    // ---------------------------
    //     GETTERS / SETTERS
    // ---------------------------

    /** @return le titre du morceau */
    public String getTitre() {
        return titre;
    }

    /** @return l'artiste du morceau */
    public String getArtiste() {
        return artiste;
    }

    /** @return l'album du morceau */
    public String getAlbum() {
        return album;
    }

    /** @return la durée du morceau en secondes */
    public int getDuree() {
        return duree;
    }

    /** Modifie le titre du morceau */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /** Modifie le nom de l'artiste */
    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    /** Modifie l'album */
    public void setAlbum(String album) {
        this.album = album;
    }

    /** Modifie la durée (en secondes) */
    public void setDuree(int duree) {
        this.duree = duree;
    }


    // ---------------------------
    //     MÉTHODES UTILES
    // ---------------------------

    /**
     * Fournit un affichage lisible des métadonnées.
     * Très utile pour le debug ou des tests.
     */
    @Override
    public String toString() {
        return "Titre : " + titre +
               ", Artiste : " + artiste +
               ", Album : " + album +
               ", Durée : " + duree + " sec";
    }
}
