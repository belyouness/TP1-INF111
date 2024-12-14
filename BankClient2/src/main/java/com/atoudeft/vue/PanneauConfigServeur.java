package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        //à compléter
        /** Q1 1.2 **/
        JLabel adrServeur = new JLabel("Adresse IP:"),
                numPort=new JLabel("Port:");

        this.txtAdrServeur=new JTextField(adr,15);
        this.txtNumPort=new JTextField(String.valueOf(port),15);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pTout = new JPanel(new GridLayout(2,1));

        p1.add(adrServeur);
        p1.add(txtAdrServeur);

        p2.add(numPort);
        p2.add(txtNumPort);

        //this.setLayout(new BorderLayout());
        pTout.add(p1, BorderLayout.CENTER);
        pTout.add(p2, BorderLayout.SOUTH);
        this.add(pTout);
        /** Q1 1.2 **/
    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
