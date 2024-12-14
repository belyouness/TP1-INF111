package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanneauDepRetTransPay extends JPanel {
    private JButton bSubmit;
    private JTextField montantField;
    private JTextField numeroFactureField;
    private JTextField descriptionFactureField;
    private JTextField compteDestinataireField;

    public PanneauDepRetTransPay(String typeOperation) {
        montantField = new JTextField();
        numeroFactureField = new JTextField();
        descriptionFactureField = new JTextField();
        compteDestinataireField = new JTextField();
        bSubmit = new JButton("envoyer");
        bSubmit.setActionCommand(typeOperation);


        setLayout(new GridLayout(5, 2, 10, 10));
        add(new JLabel("Montant:"));
        add(montantField);

        add(new JLabel("Numéro de Facture:"));
        add(numeroFactureField);

        add(new JLabel("Description de Facture:"));
        add(descriptionFactureField);

        add(new JLabel("Numéro de Compte Destinataire:"));
        add(compteDestinataireField);

        add(bSubmit);
        // Initially set fields based on the first selected operation type
        updateFields(typeOperation);

    }


    public void updateFields(String operationType) {
        switch (operationType) {
            case "FACTURE":
                montantField.setEnabled(true);
                numeroFactureField.setEnabled(true);
                descriptionFactureField.setEnabled(true);
                compteDestinataireField.setEnabled(false);
                bSubmit.setActionCommand("FACTURE");
                break;

            case "TRANSFER":
                montantField.setEnabled(true);
                numeroFactureField.setEnabled(false);
                descriptionFactureField.setEnabled(false);
                compteDestinataireField.setEnabled(true);
                bSubmit.setActionCommand("TRANSFER");
                break;
            case "RETRAIT":
                montantField.setEnabled(true);
                numeroFactureField.setEnabled(false);
                descriptionFactureField.setEnabled(false);
                compteDestinataireField.setEnabled(false);
                bSubmit.setActionCommand("RETRAIT");
                break;
            case "DEPOT":
            default:
                montantField.setEnabled(true);
                numeroFactureField.setEnabled(false);
                descriptionFactureField.setEnabled(false);
                compteDestinataireField.setEnabled(false);
                bSubmit.setActionCommand("DEPOT");
                break;
        }
    }

    public void setEcouteur(ActionListener ecouteur) {
        bSubmit.addActionListener(ecouteur);
    }

    public String getmontantField() {
        return this.montantField.getText();
    }
    public String getnumeroFactureField() {
        return this.numeroFactureField.getText();
    }
    public String getcompteDestinataireField() {
        return this.compteDestinataireField.getText();
    }
    public String getdescriptionFactureField() {
        return this.descriptionFactureField.getText();
    }
    public void effacer() {
        this.montantField.setText("");
        this.numeroFactureField.setText("");
        this.descriptionFactureField.setText("");
        this.compteDestinataireField.setText("");
    }


}
