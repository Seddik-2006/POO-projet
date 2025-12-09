package app.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe Playlist
 * ----------------
 * Représente une playlist contenant plusieurs fichiers MP3.
 *
 * Une playlist est définie par :
 * - un nom
 * - une liste de Mp3File
 *
 * Cette classe permet :
 * - d'ajouter un fichier
 * - de retirer un fichier
 * - d'accéder à un fichier par son index
 * - de connaître la taille de la playlist
 * - d'afficher son contenu
 *
 * On n'utilise que des notions vues en L2 :
 * ArrayList, Iterator, exceptions simples.
 */
public class Playlist {

    // ---------------------------
    //        ATTRIBUTS
    // ---------------------------

    /** Nom de la playlist */
    private String nom;

    /** Liste des fichiers MP3 contenus dans la playlist */
    private List<Mp3File> fichiers;


    // ---------------------------
    //      CONSTRUCTEUR
    // ---------------------------

    /**
     * Crée une playlist vide avec un nom.
     *
     * @param nom nom de la playlist
     */
    public Playlist(String nom) {
        this.nom = nom;
        this.fichiers = new ArrayList<>();
    }


    // ---------------------------
    //       MÉTHODES CRUD
    // ---------------------------

    /**
     * Ajoute un fichier MP3 à la playlist.
     *
     * @param file fichier MP3 à ajouter
     */
    public void add(Mp3File file) {
        fichiers.add(file);
    }

    /**
     * Retire un fichier MP3 de la playlist.
     *
     * @param file fichier MP3 à retirer
     */
    public void remove(Mp3File file) {
        fichiers.remove(file);
    }

    /**
     * Retourne le fichier à un index donné.
     * @param index position dans la playlist (0...n-1)
     * @return le fichier MP3
     */
    public Mp3File get(int index) {
        return fichiers.get(index);
    }

    /**
     * @return nombre total de fichiers MP3 de la playlist
     */
    public int size() {
        return fichiers.size();
    }


    // ---------------------------
    //   SÉCURITÉ : COPIE LISTE
    // ---------------------------

    /**
     * Retourne une COPIE de la liste des fichiers, pour éviter
     * que l'utilisateur modifie directement la liste interne.
     *
     * (Approche L2 : on évite List.copyOf() qui est trop avancé.)
     *
     * @return nouvelle liste contenant les mêmes éléments
     */
    public List<Mp3File> getFichiers() {
        List<Mp3File> copie = new ArrayList<>();
        for (Mp3File f : fichiers) {
            copie.add(f);  // copie des références (suffisant ici)
        }
        return copie;
    }


    // ---------------------------
    //       GETTER DU NOM
    // ---------------------------

    /** @return le nom de la playlist */
    public String getNom() {
        return nom;
    }


    // ---------------------------
    //      AFFICHAGE TEXTE
    // ---------------------------

    /**
     * Représentation textuelle de la playlist.
     * Utilise un StringBuilder pour éviter la concaténation lente.
     *
     * Exemple :
     * Playlist : Mes sons
     *  - Track 1 (C:/musique/a.mp3)
     *  - Track 2 (C:/musique/b.mp3)
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Playlist : " + nom + "\n");

        // On utilise un itérateur : notion vue en L2
        Iterator<Mp3File> it = fichiers.iterator();

        while (it.hasNext()) {
            Mp3File mp3 = it.next();

            if (mp3.getMetadata() != null) {
                sb.append(" - ")
                  .append(mp3.getMetadata().getTitre())
                  .append(" (")
                  .append(mp3.getCheminFichier())
                  .append(")\n");
            } else {
                sb.append(" - ")
                  .append(mp3.getCheminFichier())
                  .append("\n");
            }
        }

        return sb.toString();
    }
}
