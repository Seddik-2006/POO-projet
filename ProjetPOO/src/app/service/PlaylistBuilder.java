package app.service;

import app.model.Mp3File;
import app.model.Playlist;

import java.util.List;

/**
 * Classe PlaylistBuilder
 * -----------------------
 * Cette classe construit automatiquement une Playlist
 * à partir d'un dossier contenant des fichiers MP3.
 *
 * Fonctionnement :
 *   1) Explorer le dossier → liste de chemins (.mp3)
 *   2) Lire chaque fichier → conversion en Mp3File
 *   3) Ajouter dans une playlist
 *
 * C'est une classe utilitaire : pas d'attribut, uniquement une méthode statique.
 */
public class PlaylistBuilder {

    /**
     * Construit une playlist à partir d'un dossier.
     *
     * @param cheminDossier chemin du dossier contenant les fichiers MP3
     * @return une playlist remplie d'objets Mp3File (ou vide si erreur)
     */
    public static Playlist fromDirectory(String cheminDossier) {

        // ---------------------------------------
        // 1) Création d'une playlist vide
        // ---------------------------------------
        Playlist playlist = new Playlist("Playlist Générée");


        // ---------------------------------------
        // 2) Récupération des chemins MP3
        // ---------------------------------------
        // FolderExplorer cherche dans le dossier ET les sous-dossiers
        List<String> mp3Files = FolderExplorer.explorer(cheminDossier);


        // ---------------------------------------
        // 3) Conversion chemin → Mp3File
        // ---------------------------------------
        // Pour chaque fichier MP3 trouvé, on lit les métadonnées
        for (String chemin : mp3Files) {

            // On lit le fichier via la librairie mp3agic (gérée par MetadataReader)
            Mp3File mp3 = MetadataReader.lireFichier(chemin);

            // Si la lecture a réussi, on ajoute à la playlist
            if (mp3 != null) {
                playlist.add(mp3);
            }
        }

        // ---------------------------------------
        // 4) Playlist terminée
        // ---------------------------------------
        return playlist;
    }
}
