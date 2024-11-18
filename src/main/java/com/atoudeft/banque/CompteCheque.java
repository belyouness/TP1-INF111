package com.atoudeft.banque;

import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.TypeCompte;

import java.util.Date;

public class CompteCheque extends CompteBancaire {


    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */
    public CompteCheque(String numero, TypeCompte type) {
        super(numero, type);
        super.historique=new PileChainee(40);
    }

    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */



    @Override
    public boolean crediter(double montant) {

        if(montant>0){
            solde=solde+montant;
            Date date=new Date(System.currentTimeMillis());
            OperationDepot operationDepot=new OperationDepot(TypeOperation.DEPOT,date,montant);
            this.historique.empiler(operationDepot);
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {

        if(montant>0&&solde>montant){
            solde=solde-montant;
            Date date=new Date(System.currentTimeMillis());
            OperationRetrait operationRetrait=new OperationRetrait(TypeOperation.RETRAIT,date,montant);
            this.historique.empiler(operationRetrait);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {

        if(debiter(montant)){
            Date date=new Date(System.currentTimeMillis());
            OperationFacture operationFacture=new OperationFacture(TypeOperation.FACTURE,date,montant,numeroFacture,description);
            this.historique.empiler(operationFacture);
            return true;
        }

        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {return false;}
}
