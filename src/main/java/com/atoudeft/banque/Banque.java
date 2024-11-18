package com.atoudeft.banque;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Banque implements Serializable {
    private String nom;
    private List<CompteClient> comptes;

    public Banque(String nom) {
        this.nom = nom;
        this.comptes = new ArrayList<>();
    }

    /**
     * Recherche un compte-client à partir de son numéro.
     *
     * @param numeroCompteClient le numéro du compte-client
     * @return le compte-client s'il a été trouvé. Sinon, retourne null
     */
    public CompteClient getCompteClient(String numeroCompteClient) {
        CompteClient cpt = new CompteClient(numeroCompteClient,"");
        int index = this.comptes.indexOf(cpt);
        if (index != -1)
            return this.comptes.get(index);
        else
            return null;
    }

    /**
     * Vérifier qu'un compte-bancaire appartient bien au compte-client.
     *
     * @param numeroCompteBancaire numéro du compte-bancaire
     * @param numeroCompteClient    numéro du compte-client
     * @return  true si le compte-bancaire appartient au compte-client
     */
    public boolean appartientA(String numeroCompteBancaire, String numeroCompteClient) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un dépot d'argent dans un compte-bancaire
     *
     * @param montant montant à déposer
     * @param numeroCompte numéro du compte
     * @return true si le dépot s'est effectué correctement
     */
    public boolean deposer(double montant, String numeroCompte) {
        CompteBancaire bnc=getCompte(numeroCompte);
        if(bnc.getType()==TypeCompte.CHEQUE||bnc.getType()==TypeCompte.EPARGNE){
            bnc.crediter(montant);
            return true;
        }
        else
        return false;
    }

    /**
     * Effectue un retrait d'argent d'un compte-bancaire
     *
     * @param montant montant retiré
     * @param numeroCompte numéro du compte
     * @return true si le retrait s'est effectué correctement
     */
    public boolean retirer(double montant, String numeroCompte) {
        CompteBancaire bnc=getCompte(numeroCompte);
        if(bnc.getType()==TypeCompte.CHEQUE||bnc.getType()==TypeCompte.EPARGNE){
            bnc.debiter(montant);
            return true;
        }
        else
            return false;
    }

    /**
     * Effectue un transfert d'argent d'un compte à un autre de la même banque
     * @param montant montant à transférer
     * @param numeroCompteInitial   numéro du compte d'où sera prélevé l'argent
     * @param numeroCompteFinal numéro du compte où sera déposé l'argent
     * @return true si l'opération s'est déroulée correctement
     */
    public boolean transferer(double montant, String numeroCompteInitial, String numeroCompteFinal) {
        CompteBancaire compteIni=getCompte(numeroCompteInitial);
        CompteBancaire compteDes=getCompte(numeroCompteFinal);
        if(compteIni.getType()==TypeCompte.CHEQUE||compteIni.getType()==TypeCompte.EPARGNE){
           if (compteIni.debiter(montant)){
               compteDes.crediter(montant);
               return true;
           }
           else
               return false;
        }
        else
            return false;
    }

    /**
     * Effectue un paiement de facture.
     * @param montant montant de la facture
     * @param numeroCompte numéro du compte bancaire d'où va se faire le paiement
     * @param numeroFacture numéro de la facture
     * @param description texte descriptif de la facture
     * @return true si le paiement s'est bien effectuée
     */
    public boolean payerFacture(double montant, String numeroCompte, String numeroFacture, String description) {
        CompteBancaire bnc=getCompte(numeroCompte);
        if(bnc.getType()==TypeCompte.CHEQUE||bnc.getType()==TypeCompte.EPARGNE){
            bnc.payerFacture(numeroFacture,montant,description);
            return true;
        }
        else
            return false;

    }

    /**
     * Crée un nouveau compte-client avec un numéro et un nip et l'ajoute à la liste des comptes.
     *
     * @param numCompteClient numéro du compte-client à créer
     * @param nip nip du compte-client à créer
     * @return true si le compte a été créé correctement
     */
    public boolean ajouter(String numCompteClient, String nip) {
        /*À compléter et modifier :
            - Vérifier que le numéro a entre 6 et 8 caractères et ne contient que des lettres majuscules et des chiffres.
              Sinon, retourner false.
            - Vérifier que le nip a entre 4 et 5 caractères et ne contient que des chiffres. Sinon,
              retourner false.
            - Vérifier s'il y a déjà un compte-client avec le numéro, retourner false.
            - Sinon :
                . Créer un compte-client avec le numéro et le nip;
                . Générer (avec CompteBancaire.genereNouveauNumero()) un nouveau numéro de compte bancaire qui n'est
                  pas déjà utilisé;
                . Créer un compte-chèque avec ce numéro et l'ajouter au compte-client;
                . Ajouter le compte-client à la liste des comptes et retourner true.
         */

        if(numCompteClient.length()>=6&&numCompteClient.length()<=8
           &&numCompteClient.matches("[A-Z0-9 ]*")&&nip.matches("[0-9]*")){

            for (CompteClient con:comptes
                 ) {
                if(numCompteClient==con.numero){
                    return false;
                }
            }
            CompteClient compteCl=new CompteClient(numCompteClient,nip);
            String numBank=CompteBancaire.genereNouveauNumero();
            CompteCheque compCh=new CompteCheque(numBank,TypeCompte.CHEQUE);
            compteCl.ajouter(compCh);

            comptes.add(compteCl);
            return true;
            //return this.comptes.add(new CompteClient(numCompteClient,nip)); //À modifier
        }
        else{
        return false;
        }
    }

    /**
     * Retourne le numéro du compte-chèque d'un client à partir de son numéro de compte-client.
     *
     * @param numCompteClient numéro de compte-client
     * @return numéro du compte-chèque du client ayant le numéro de compte-client
     */
    public String getNumeroCompteParDefaut(String numCompteClient) {
        //À compléter : retourner le numéro du compte-chèque du compte-client.
        for (CompteClient client:comptes
             ) {
            if(client.numero==numCompteClient){
                for (CompteBancaire ban: client.comptes
                     ) {
                    if(ban.getType()==TypeCompte.CHEQUE){
                        return ban.getNumero();
                    }
                }
            }
        }
         return null; /** verifier avec prof si un seul return ou pas **/
    }

    /** get le num du compte-epargne du client si il existe **/
    public String getNumeroCompteEpargne(String numCompteClient) {
        //À compléter : retourner le numéro du compte-epargne du compte-client si il existe.
        for (CompteClient client:comptes
        ) {
            if(client.numero==numCompteClient){
                for (CompteBancaire ban: client.comptes
                ) {
                    if(ban.getType()==TypeCompte.EPARGNE){
                        return ban.getNumero();
                    }
                }
            }
        }
        return null;
    }

    /** Recupere la liste des numeros de chaque compte de la banque pour simplifier verification au niveau de nouveau compte bancaire pour client **/
    public ArrayList<String> getListeCompteNum() {
        ArrayList<String> str=new ArrayList<>();
        for (CompteClient client:comptes
        ) {
            for (CompteBancaire bnc: client.comptes
                 ) {
                str.add(bnc.getNumero());
            }
        }
        return str;
    }

    /** Recupere un compte specifique et le retounre (simplifier le process de deposer/retirer) **/
    public CompteBancaire getCompte(String numCompte){
        CompteBancaire cbd = null;
        for (CompteClient c:comptes
             ) {
            for (CompteBancaire bnc: c.comptes
            ) {
                if(bnc.getNumero().equals(numCompte)){
                    cbd=bnc;
                }
            }
        }

        return cbd;
    }

    /** 7.7 Historique **/
    public String getHistoriqueCompteSeelcted(String numCompteSelected){
        String str="HIS ";
        CompteBancaire compteSeHis=getCompte(numCompteSelected);
        while(!compteSeHis.historique.estVide()){
          String  valeur = compteSeHis.historique.depiler().toString();
            str+=valeur+" \n ";
        }
        return str;
    }
}