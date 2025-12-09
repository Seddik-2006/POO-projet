package app.service;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import java.io.FileWriter;
import java.io.IOException;

public class M3U8Exporter {

    public static boolean exporter(Playlist playlist, String cheminSortie) {

        try (FileWriter writer = new FileWriter(cheminSortie)) {

            writer.write("#EXTM3U\n");

            // On boucle sur les Mp3File de la playlist
            for (Mp3File mp3 : playlist.getFichiers()) {

                Metadata meta = mp3.getMetadata();

                int duree = (meta != null) ? meta.getDuree() : -1;
                String titre = (meta != null) ? meta.getTitre() : "Inconnu";
                String artiste = (meta != null) ? meta.getArtiste() : "Inconnu";

                // Ligne d'information (optionnelle mais standard pour M3U8)
                writer.write("#EXTINF:" + duree + "," + artiste + " - " + titre + "\n");

                // Chemin du fichier (normalisé)
                String path = mp3.getCheminFichier().replace("\\", "/");
                writer.write(path + "\n");
            }

            return true;

        } catch (IOException e) {
            System.out.println("Erreur export M3U8 : " + e.getMessage());
            return false;
        }
    }
}
