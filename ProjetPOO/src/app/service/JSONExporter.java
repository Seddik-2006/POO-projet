package app.service;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import java.io.FileWriter;
import java.io.IOException;

public class JSONExporter {

    public static boolean exporter(Playlist playlist, String cheminSortie) {

        try (FileWriter writer = new FileWriter(cheminSortie)) {

            writer.write("{\n");
            writer.write("  \"title\": \"" + playlist.getNom() + "\",\n");
            writer.write("  \"tracks\": [\n");

            int size = playlist.size();
            int index = 0;

            for (Mp3File mp3 : playlist.getFichiers()) {

                Metadata m = mp3.getMetadata();
                String titre = m != null ? m.getTitre() : "Inconnu";
                String artiste = m != null ? m.getArtiste() : "Inconnu";
                String album = m != null ? m.getAlbum() : "Inconnu";
                int duree = m != null ? m.getDuree() : -1;

                String path = mp3.getCheminFichier().replace("\\", "/");

                writer.write("    {\n");
                writer.write("      \"title\": \"" + titre + "\",\n");
                writer.write("      \"artist\": \"" + artiste + "\",\n");
                writer.write("      \"album\": \"" + album + "\",\n");
                writer.write("      \"duration\": " + duree + ",\n");
                writer.write("      \"location\": \"file:///" + path + "\"\n");
                writer.write("    }");

                if (index < size - 1) writer.write(",");
                writer.write("\n");
                index++;
            }

            writer.write("  ]\n");
            writer.write("}\n");

            return true;

        } catch (IOException e) {
            System.out.println("Erreur export JSON : " + e.getMessage());
            return false;
        }
    }
}
