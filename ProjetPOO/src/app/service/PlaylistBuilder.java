package app.service;

import app.model.Mp3File;
import app.model.Playlist;

import java.util.List;

public class PlaylistBuilder {

    public static Playlist fromDirectory(String cheminDossier) {

        // 1) Créer une playlist
        Playlist playlist = new Playlist("Playlist Générée");

        // 2) Récupérer tous les fichiers MP3 du dossier
        List<String> mp3Files = FolderExplorer.explorer(cheminDossier);

        // 3) Pour chaque chemin MP3, extraire les métadonnées
        for (String chemin : mp3Files) {

            Mp3File mp3 = MetadataReader.lireFichier(chemin);

            // Si le fichier est valide, on l'ajoute à la playlist
            if (mp3 != null) {
                playlist.add(mp3);
            }
        }

        return playlist;
        
        
    }
}
