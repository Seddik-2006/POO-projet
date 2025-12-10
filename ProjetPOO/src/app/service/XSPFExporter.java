package app.service;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe XSPFExporter
 * --------------------
 * Cette classe exporte une playlist au format XSPF.
 *
 * XSPF (XML Shareable Playlist Format) est un format basé sur XML.
 *
 * Structure minimale :
 *   <?xml version="1.0" encoding="UTF-8"?>
 *   <playlist version="1" xmlns="http://xspf.org/ns/0/">
 *       <title>Nom Playlist</title>
 *       <trackList>
 *           <track> ... </track>
 *       </trackList>
 *   </playlist>
 *
 * Ici on utilise uniquement FileWriter → niveau L2.
 */
public class XSPFExporter {

    /**
     * Exporte une playlist en XML/XSPF.
     *
     * @param playlist     playlist à exporter
     * @param cheminSortie chemin du fichier .xspf à créer
     * @return true si l'écriture réussit, false si erreur
     */
    public static boolean exporter(Playlist playlist, String cheminSortie) {

        // try-with-resources → ferme automatiquement le FileWriter
        try (FileWriter writer = new FileWriter(cheminSortie)) {

            // --------------------------------------------------------------
            // 1) En-tête XML obligatoire
            // --------------------------------------------------------------
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            // Balise principale du format XSPF
            writer.write("<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\n");

            // Titre de la playlist
            writer.write("  <title>" + playlist.getNom() + "</title>\n");

            // --------------------------------------------------------------
            // 2) Liste des tracks (pistes MP3)
            // --------------------------------------------------------------
            writer.write("  <trackList>\n");

            // Pour chaque MP3 dans la playlist
            for (Mp3File mp3 : playlist.getFichiers()) {

                Metadata meta = mp3.getMetadata();

                // Valeurs ou valeurs par défaut
                String titre   = (meta != null) ? meta.getTitre()   : "Inconnu";
                String artiste = (meta != null) ? meta.getArtiste() : "Inconnu";
                String album   = (meta != null) ? meta.getAlbum()   : "Inconnu";
                int duree      = (meta != null) ? meta.getDuree()   : -1;

                // Normalisation du chemin (remplacement des \ en /)
                String path = mp3.getCheminFichier().replace("\\", "/");


                // ----------------------------------------------------------
                // 3) Balises d’un track XSPF
                // ----------------------------------------------------------
                writer.write("    <track>\n");
                writer.write("      <title>" + titre + "</title>\n");
                writer.write("      <creator>" + artiste + "</creator>\n");
                writer.write("      <album>" + album + "</album>\n");

                // Le format XSPF demande la durée en millisecondes
                writer.write("      <duration>" + (duree * 1000) + "</duration>\n");

                writer.write("      <location>file:///" + path + "</location>\n");
                writer.write("    </track>\n");
            }

            // Fermeture des balises trackList et playlist
            writer.write("  </trackList>\n");
            writer.write("</playlist>\n");

            return true; // succès

        } catch (IOException e) {

            // --------------------------------------------------------------
            // 4) Gestion d’erreur simple et claire
            // --------------------------------------------------------------
            System.out.println("Erreur lors de l'export XSPF : " + e.getMessage());
            return false;
        }
    }
}
