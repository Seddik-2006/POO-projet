package app.service;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe JSONExporter
 * --------------------
 * Cette classe exporte une playlist au format JSON.
 *
 * Format généré :
 * {
 *   "title": "Nom playlist",
 *   "tracks": [
 *      { "title": "...", "artist": "...", "album": "...", "duration": ..., "location": "file:///..." }
 *   ]
 * }
 *
 * Aucun framework JSON → uniquement FileWriter (niveau L2)
 */
public class JSONExporter {

    /**
     * Exporte une playlist dans un fichier JSON.
     *
     * @param playlist     playlist à exporter
     * @param cheminSortie chemin du fichier JSON à créer
     * @return true si l'écriture a réussi, false sinon
     */
    public static boolean exporter(Playlist playlist, String cheminSortie) {

        // try-with-resources : ferme automatiquement le FileWriter
        try (FileWriter writer = new FileWriter(cheminSortie)) {

            // -------------------------------------------------------
            // 1) Début du JSON : informations générales
            // -------------------------------------------------------
            writer.write("{\n");
            writer.write("  \"title\": \"" + playlist.getNom() + "\",\n");
            writer.write("  \"tracks\": [\n");


            int size = playlist.size();  // nombre total de pistes
            int index = 0;               // pour gérer la virgule finale


            // -------------------------------------------------------
            // 2) Parcourir la playlist (boucle for-each L2)
            // -------------------------------------------------------
            for (Mp3File mp3 : playlist.getFichiers()) {

                Metadata m = mp3.getMetadata();

                // Valeurs par défaut si métadonnées absentes
                String titre   = (m != null) ? m.getTitre()   : "Inconnu";
                String artiste = (m != null) ? m.getArtiste() : "Inconnu";
                String album   = (m != null) ? m.getAlbum()   : "Inconnu";
                int duree      = (m != null) ? m.getDuree()   : -1;

                // Le chemin doit être compatible JSON (slashes "/")
                String path = mp3.getCheminFichier().replace("\\", "/");


                // ---------------------------------------------------
                // 3) Écriture de chaque piste en JSON
                // ---------------------------------------------------
                writer.write("    {\n");
                writer.write("      \"title\": \"" + titre + "\",\n");
                writer.write("      \"artist\": \"" + artiste + "\",\n");
                writer.write("      \"album\": \"" + album + "\",\n");
                writer.write("      \"duration\": " + duree + ",\n");
                writer.write("      \"location\": \"file:///" + path + "\"\n");
                writer.write("    }");

                // Virgule entre les éléments sauf le dernier
                if (index < size - 1) {
                    writer.write(",");
                }
                writer.write("\n");

                index++;
            }


            // -------------------------------------------------------
            // 4) Fermeture des balises JSON
            // -------------------------------------------------------
            writer.write("  ]\n");
            writer.write("}\n");

            return true;  // succès

        } catch (IOException e) {

            // -------------------------------------------------------
            // 5) Gestion d’erreur simple (niveau L2)
            // -------------------------------------------------------
            System.out.println("Erreur export JSON : " + e.getMessage());
            return false;
        }
    }
}
