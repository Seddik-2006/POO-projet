package app.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

import app.model.Mp3File;
import app.model.Metadata;
import app.service.FolderExplorer;
import app.service.MetadataReader;

public class FenetrePrincipale extends JFrame {

    private DefaultListModel<String> modelListeMP3;
    private JList<String> listeMP3;

    private JLabel labelTitre;
    private JLabel labelArtiste;
    private JLabel labelAlbum;
    private JLabel labelDuree;

    public FenetrePrincipale() {

        setTitle("MP3 Manager - Interface Graphique");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =====================
        // BARRE DE MENU
        // =====================
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem ouvrir = new JMenuItem("Ouvrir un dossier");

        ouvrir.addActionListener(e -> choisirDossier());

        menuFichier.add(ouvrir);
        menuBar.add(menuFichier);
        setJMenuBar(menuBar);

        // =====================
        // PANNEAU GAUCHE : LISTE MP3
        // =====================
        modelListeMP3 = new DefaultListModel<>();
        listeMP3 = new JList<>(modelListeMP3);

        listeMP3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listeMP3.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String chemin = listeMP3.getSelectedValue();
                afficherMetadata(chemin);
            }
        });

        JScrollPane scrollListe = new JScrollPane(listeMP3);
        scrollListe.setBorder(BorderFactory.createTitledBorder("Liste des fichiers MP3"));

        // =====================
        // PANNEAU DROITE : METADONNEES
        // =====================
        JPanel panelMetadata = new JPanel(new GridLayout(4, 2, 10, 10));
        panelMetadata.setBorder(BorderFactory.createTitledBorder("Métadonnées du fichier sélectionné"));

        labelTitre = new JLabel("-");
        labelArtiste = new JLabel("-");
        labelAlbum = new JLabel("-");
        labelDuree = new JLabel("-");

        panelMetadata.add(new JLabel("Titre :"));
        panelMetadata.add(labelTitre);

        panelMetadata.add(new JLabel("Artiste :"));
        panelMetadata.add(labelArtiste);

        panelMetadata.add(new JLabel("Album :"));
        panelMetadata.add(labelAlbum);

        panelMetadata.add(new JLabel("Durée :"));
        panelMetadata.add(labelDuree);

        // =====================
        // LAYOUT GLOBAL
        // =====================
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollListe, panelMetadata);
        split.setDividerLocation(400);
        add(split);

        setVisible(true);
    }

    // =================================
    // FONCTION POUR CHOISIR UN DOSSIER
    // =================================
    private void choisirDossier() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int resultat = chooser.showOpenDialog(this);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            File dossier = chooser.getSelectedFile();
            chargerDossier(dossier.getAbsolutePath());
        }
    }

    // =================================
    // CHARGER LES FICHIERS MP3 DU DOSSIER
    // =================================
    private void chargerDossier(String chemin) {
        modelListeMP3.clear();

        List<String> fichiers = FolderExplorer.explorer(chemin);

        for (String f : fichiers) {
            modelListeMP3.addElement(f);
        }
    }

    // =================================
    // AFFICHAGE DES METADONNEES
    // =================================
    private void afficherMetadata(String chemin) {

        Mp3File fichier = MetadataReader.lireFichier(chemin);

        if (fichier == null) {
            labelTitre.setText("-");
            labelArtiste.setText("-");
            labelAlbum.setText("-");
            labelDuree.setText("-");
            return;
        }

        Metadata m = fichier.getMetadata();

        labelTitre.setText(m.getTitre());
        labelArtiste.setText(m.getArtiste());
        labelAlbum.setText(m.getAlbum());
        labelDuree.setText(m.getDuree() + " sec");
    }
}
