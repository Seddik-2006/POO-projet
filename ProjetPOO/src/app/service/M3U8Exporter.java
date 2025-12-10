package app.service;

import app.model.Mp3File;
import app.model.Metadata;
import app.model.Playlist;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe M3U8Exporter
 * --------------------
 * Cette classe exporte une playlist au format M3U8.
 *
 * Format M3U8 :
 *   #EXTM3U
 *   #EXTINF:durée, artiste - titre
 *   chemin/vers/le/fichier.mp3
 *
 * M3U8 est un format simple, basé sur des lignes de texte.
 * Ici, on utilise uniquement FileWriter (niveau L2).
 */
public class M3U8Exporter {

    /**
     * Exporte une playlist au format M3U8.
     *
     * @param playlist     playlist à exporter
     * @param cheminSortie chemin du fichier .m3u8 à créer
     * @return true si l'écriture est réussie, false sinon
     */
    public static boolean exporter(Playlist playlist, String cheminSortie) {

        // try-with-resources : ferme automatiquement le writer après usage
        try (FileWriter writer = new FileWriter(cheminSortie)) {

            // ------------------------------------------------------
            // 1) Ligne obligatoire dans un fichier M3U/M3U8
            // ------------------------------------------------------
            writer.write("#EXTM3U\n");

            // ------------------------------------------------------
            // 2) Parcours de toutes les pistes de la playlist
            // ------------------------------------------------------
            for (Mp3File mp3 : playlist.getFichiers()) {

                Metadata meta = mp3.getMetadata();

                // Récupération des valeurs, ou valeurs par défaut
                int duree      = (meta != null) ? meta.getDuree()   : -1;
                String titre   = (meta != null) ? meta.getTitre()   : "Inconnu";
                String artiste = (meta != null) ? meta.getArtiste() : "Inconnu";

                // --------------------------------------------------
                // 3) Ligne d'information : #EXTINF
                // Format : "#EXTINF:durée, artiste - titre"
                // --------------------------------------------------
                writer.write("#EXTINF:" + duree + "," + artiste + " - " + titre + "\n");

                // --------------------------------------------------
                // 4) Ligne contenant le chemin du fichier
                // --------------------------------------------------
                String path = mp3.getCheminFichier().replace("\\", "/");
                writer.write(path + "\n");
            }

            return true;  // succès

        } catch (IOException e) {

            // ------------------------------------------------------
            // 5) Gestion d'erreur (simple et compréhensible)
            // ------------------------------------------------------
            System.out.println("Erreur export M3U8 : " + e.getMessage());
            return false;
        }
    }
}
