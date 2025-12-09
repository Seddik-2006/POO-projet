package app.gui;

import javax.swing.*;
import java.awt.*;

import app.model.Metadata;

public class PanneauMetadata extends JPanel {

    private JLabel lblTitreValeur;
    private JLabel lblArtisteValeur;
    private JLabel lblAlbumValeur;
    private JLabel lblDureeValeur;

    public PanneauMetadata() {

        setLayout(new BorderLayout());
        
        JLabel titre = new JLabel("Métadonnées du fichier sélectionné");
        titre.setHorizontalAlignment(SwingConstants.CENTER);

        add(titre, BorderLayout.NORTH);

        // Panel central avec un layout en grille
        JPanel centre = new JPanel(new GridLayout(4, 2, 5, 5));

        centre.add(new JLabel("Titre : "));
        lblTitreValeur = new JLabel("-");
        centre.add(lblTitreValeur);

        centre.add(new JLabel("Artiste : "));
        lblArtisteValeur = new JLabel("-");
        centre.add(lblArtisteValeur);

        centre.add(new JLabel("Album : "));
        lblAlbumValeur = new JLabel("-");
        centre.add(lblAlbumValeur);

        centre.add(new JLabel("Durée : "));
        lblDureeValeur = new JLabel("-");
        centre.add(lblDureeValeur);

        add(centre, BorderLayout.CENTER);
    }

    /**
     * Met à jour l'affichage des métadonnées
     */
    public void afficherMetadata(Metadata meta) {

        if (meta == null) {
            lblTitreValeur.setText("-");
            lblArtisteValeur.setText("-");
            lblAlbumValeur.setText("-");
            lblDureeValeur.setText("-");
            return;
        }

        lblTitreValeur.setText(meta.getTitre());
        lblArtisteValeur.setText(meta.getArtiste());
        lblAlbumValeur.setText(meta.getAlbum());
        lblDureeValeur.setText(meta.getDuree() + " sec");
    }
}

