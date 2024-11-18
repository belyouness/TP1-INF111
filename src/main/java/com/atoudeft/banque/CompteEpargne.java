package com.atoudeft.banque;

import java.util.Date;

public class CompteEpargne extends CompteBancaire{
    final  int limit=1000;
    final int frais=2;

    public CompteEpargne(String numero, TypeCompte type, Double interets) {
        super(numero, type, interets);
        super.historique=new PileChainee(40);
    }

    public void ajouterInterets(){
        solde= (solde*interets)+solde;
    }

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
            if(solde<limit){
                solde=(solde-montant)-frais;
            }
            else {
                solde=solde-montant;
            }
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
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }
}
