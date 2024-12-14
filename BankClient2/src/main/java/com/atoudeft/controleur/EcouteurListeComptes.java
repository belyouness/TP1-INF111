package com.atoudeft.controleur;

import com.atoudeft.client.Client;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurListeComptes extends MouseAdapter {

    private Client client;
    public EcouteurListeComptes(Client client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        //à compléter
       // System.out.println( "click reactif fix the rest");
        if (evt.getClickCount()%2==0 && !evt.isConsumed()){
            evt.consume();
            Object source = evt.getSource();
            if(source instanceof JList ){
               // JList<String> jSTR=((JList)source);
                String sel=((JList)source).getSelectedValue().toString();
                /** serveur semble vouloir recevoir lower case pour le type de compte (pas de ignore case) donc for now on lowercase**/
                String compteTy=sel.substring(sel.indexOf("[")+1,sel.indexOf("]")).toLowerCase(Locale.ROOT);

                client.envoyer("SELECT "+compteTy);
            }
        }
    }
}
