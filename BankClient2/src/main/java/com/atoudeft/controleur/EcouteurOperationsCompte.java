package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauDepRetTransPay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;
    private PanneauDepRetTransPay pan;


    public EcouteurOperationsCompte(Client client,PanneauDepRetTransPay panneauDepRetTransPay) {
        this.client = client;
        this.pan=panneauDepRetTransPay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //à compléter :
        Object source = e.getSource();
        String nomAction;
        String nameButoon;
        if (source instanceof JButton) {
            nomAction = ((JButton) source).getActionCommand();
            nameButoon=(((JButton)source).getText());
            switch (nomAction) {
                case "EPARGNE":
                    client.envoyer("EPARGNE");
                    break;

                    /** Q4 4. **/
                case "DEPOT":
                    pan.updateFields(nomAction);
                    if(nameButoon.equals("envoyer")){client.envoyer("DEPOT "+pan.getmontantField());pan.effacer();}
                    break;
                case "RETRAIT":
                    pan.updateFields(nomAction);
                    if(nameButoon.equals("envoyer"))
                    {
                        client.envoyer("RETRAIT "+pan.getmontantField());
                        pan.effacer();
                    }
                    break;
                case "TRANSFER":
                    pan.updateFields(nomAction);
                    if(nameButoon.equals("envoyer"))
                    {
                        client.envoyer("TRANSFER "+pan.getmontantField()+" "+pan.getcompteDestinataireField());
                        pan.effacer();
                    }
                    break;
                case "FACTURE":
                    pan.updateFields(nomAction);
                    if(nameButoon.equals("envoyer"))
                    {
                        client.envoyer("FACTURE "+pan.getmontantField()+" "
                                                    +pan.getnumeroFactureField()+" "
                                                    +pan.getdescriptionFactureField());
                        pan.effacer();
                    }
                    break;
            }
        }
    }
}
