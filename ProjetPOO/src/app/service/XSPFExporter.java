package app.service;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import java.io.FileWriter;
import java.io.IOException;

public class XSPFExporter {

    public static boolean exporter(Playlist playlist, String cheminSortie) {

        try (FileWriter writer = new FileWriter(cheminSortie)) {

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\n");

            // Titre de la playlist
            writer.write("  <title>" + playlist.getNom() + "</title>\n");

            writer.write("  <trackList>\n");

            for (Mp3File mp3 : playlist.getFichiers()) {

                Metadata meta = mp3.getMetadata();

                String titre = meta != null ? meta.getTitre() : "Inconnu";
                String artiste = meta != null ? meta.getArtiste() : "Inconnu";
                String album = meta != null ? meta.getAlbum() : "Inconnu";
                int duree = meta != null ? meta.getDuree() : -1;

                String path = mp3.getCheminFichier().replace("\\", "/");

                writer.write("    <track>\n");
                writer.write("      <title>" + titre + "</title>\n");
                writer.write("      <creator>" + artiste + "</creator>\n");
                writer.write("      <album>" + album + "</album>\n");
                writer.write("      <duration>" + (duree * 1000) + "</duration>\n");
                writer.write("      <location>file:///" + path + "</location>\n");
                writer.write("    </track>\n");
            }

            writer.write("  </trackList>\n");
            writer.write("</playlist>\n");

            return true;

        } catch (IOException e) {
            System.out.println("Erreur lors de l'export XSPF : " + e.getMessage());
            return false;
        }
    }
}
