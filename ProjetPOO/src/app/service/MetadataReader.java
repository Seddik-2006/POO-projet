package app.service;

import app.model.Metadata;
import app.model.Mp3File;
import com.mpatric.mp3agic.ID3v2;

/**
 * Classe MetadataReader
 * ----------------------
 * Cette classe contient une seule responsabilité :
 *      → Lire un fichier MP3 (via la librairie mp3agic)
 *      → Extraire ses métadonnées (titre, artiste, album, durée)
 *      → Construire un objet Mp3File de notre application
 *
 * C'est une classe utilitaire : pas d'attribut, pas d'état.
 */
public class MetadataReader {

    /**
     * Méthode qui lit un fichier MP3 et renvoie un objet Mp3File.
     *
     * @param cheminFichier chemin complet du fichier MP3
     * @return objet Mp3File contenant chemin + metadata,
     *         ou null si une erreur est survenue.
     */
    public static Mp3File lireFichier(String cheminFichier) {

        try {
            // -------------------------------
            // 1) Lecture réelle du fichier MP3
            // -------------------------------
            // On utilise la classe Mp3File de la LIBRAIRIE mp3agic
            // Ce n'est PAS notre classe Mp3File.
            com.mpatric.mp3agic.Mp3File mp3Lib =
                    new com.mpatric.mp3agic.Mp3File(cheminFichier);


            // -------------------------------
            // 2) Valeurs par défaut
            // -------------------------------
            // Si le fichier n'a aucune métadonnée, on met ces valeurs
            String titre   = "Inconnu";
            String artiste = "Inconnu";
            String album   = "Inconnu";

            // → mp3agic peut calculer la durée automatiquement
            int duree = (int) mp3Lib.getLengthInSeconds();


            // -------------------------------
            // 3) Extraction des tags ID3v2
            // -------------------------------
            // Beaucoup de fichiers MP3 n'ont PAS de tags.
            // On vérifie donc avant d'y accéder.
            if (mp3Lib.hasId3v2Tag()) {
                ID3v2 tag = mp3Lib.getId3v2Tag();

                // On lit chaque champ uniquement s'il existe
                if (tag.getTitle() != null && !tag.getTitle().isBlank()) {
                    titre = tag.getTitle();
                }

                if (tag.getArtist() != null && !tag.getArtist().isBlank()) {
                    artiste = tag.getArtist();
                }

                if (tag.getAlbum() != null && !tag.getAlbum().isBlank()) {
                    album = tag.getAlbum();
                }
            }


            // -------------------------------
            // 4) Construction des objets métier
            // -------------------------------
            // Notre classe Metadata → pour stocker les infos
            Metadata metadata = new Metadata(titre, artiste, album, duree);

            // Notre classe Mp3File → fichier + metadata
            return new Mp3File(cheminFichier, metadata);


        } catch (Exception e) {

            // -------------------------------
            // 5) Gestion d'erreur L2 simple
            // -------------------------------
            System.out.println("Erreur lors de la lecture : " + cheminFichier);
            System.out.println(e.getClass().getSimpleName() + " : " + e.getMessage());

            // On retourne null → l'appelant devra vérifier
            return null;
        }
    }
}
