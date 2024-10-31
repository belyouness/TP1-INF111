package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire{
    final  int limit=1000;
    final int frais=2;

    public CompteEpargne(String numero, TypeCompte type, Double interets) {
        super(numero, type, interets);
    }

    public void ajouterInterets(){
        solde= (solde*interets)+solde;
    }

    @Override
    public boolean crediter(double montant) {
        if(montant>0){
            solde=solde+montant;
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
            return true;
        }
        return false;

    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }
}
