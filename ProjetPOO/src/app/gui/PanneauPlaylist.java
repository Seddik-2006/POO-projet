package app.gui;

import javax.swing.*;
import java.awt.*;
import app.model.Playlist;
import app.model.Mp3File;

public class PanneauPlaylist extends JPanel {

    private JList<Mp3File> listeFichiers;
    private DefaultListModel<Mp3File> model;

    public PanneauPlaylist() {

        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Liste des fichiers MP3");
        titre.setHorizontalAlignment(SwingConstants.CENTER);

        model = new DefaultListModel<>();
        listeFichiers = new JList<>(model);

        listeFichiers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(listeFichiers);

        add(titre, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Affiche la playlist dans le panneau et connecte les clics
     */
    public void afficherPlaylist(Playlist playlist, PanneauMetadata metadataPanel) {

        model.clear();

        for (Mp3File f : playlist.getFichiers()) {
            model.addElement(f);
        }

        // On ajoute le listener pour afficher les métadonnées
        listeFichiers.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                Mp3File selection = listeFichiers.getSelectedValue();
                if (selection != null) {
                    metadataPanel.afficherMetadata(selection.getMetadata());
                }
            }
        });
    }
}