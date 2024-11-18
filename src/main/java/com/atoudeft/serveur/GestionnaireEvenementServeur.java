package com.atoudeft.serveur;

import com.atoudeft.banque.*;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        ServeurBanque serveurBanque = (ServeurBanque)serveur;
        Banque banque;
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip;
        String[] t;

        if (source instanceof Connexion) {
            cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            cnx.setTempsDerniereOperation(System.currentTimeMillis());
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveurBanque.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des numéros de comptes-clients connectés :
                    cnx.envoyer("LIST " + serveurBanque.list());
                    break;
                /******************* COMMANDES DE GESTION DE COMPTES *******************/
                case "NOUVEAU": //Crée un nouveau compte-client :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO deja connecte");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("NOUVEAU NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.ajouter(numCompteClient,nip)) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("NOUVEAU OK " + t[0] + " cree");
                        }
                        else
                            cnx.envoyer("NOUVEAU NO "+t[0]+" existe");
                    }
                    break;
                /******************* METHODE DE CONNEXION *******************/
                case "CONNECT": // permet client de se connecter
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    numCompteClient=t[0];
                    nip=t[1];

                    for (Connexion cn: serveur.connectes
                         ) {
                        if(((ConnexionBanque)cn).getNumeroCompteClient()!=null&&((ConnexionBanque)cn).getNumeroCompteClient().equals(numCompteClient)){
                            cnx.envoyer("CONNECT NO");
                            return;
                        }
                    }

                    CompteClient client=serveurBanque.getBanque().getCompteClient(numCompteClient);
                    if(client==null||!client.getNip().equals(nip)){
                        cnx.envoyer("CONNECT NO");
                        break;
                    }
                    else{
                        cnx.setNumeroCompteClient(numCompteClient);
                        cnx.setNumeroCompteActuel(serveurBanque.getBanque().getNumeroCompteParDefaut(numCompteClient));
                        cnx.envoyer("CONNECT OK");
                        break;
                    }
                /******************* METHODE EPARGNE *******************/
                case "EPARGNE": //permet au client de creer un compte epargne si n'existe pas deja
                    if (cnx.getNumeroCompteClient()==null||serveurBanque.getBanque().getNumeroCompteEpargne(cnx.getNumeroCompteClient())!=null) {
                        cnx.envoyer("EPARGNE NO");
                        return;
                    }
                    String numepargneGen= CompteBancaire.genereNouveauNumero();
                    while (serveurBanque.getBanque().getListeCompteNum().contains(numepargneGen)){
                        numepargneGen= CompteBancaire.genereNouveauNumero();
                    }

                    CompteEpargne epargne=new CompteEpargne(numepargneGen, TypeCompte.EPARGNE,0.05);
                    serveurBanque.getBanque().getCompteClient(cnx.getNumeroCompteClient()).ajouter(epargne);
                    cnx.envoyer("EPARGNE OUI");
                    break;
                /******************* METHODE SELECT *******************/
                case "SELECT":
                    if(cnx.getNumeroCompteClient()==null){
                        cnx.envoyer("SELECT NO");
                        return;
                    }
                    argument=evenement.getArgument();
                    if(argument.equals("cheque")){

                        String cheque=serveurBanque.getBanque().getNumeroCompteParDefaut(cnx.getNumeroCompteClient());
                        cnx.setNumeroCompteActuel(cheque);
                        cnx.envoyer("SELECT OK");
                        return;
                    }
                    else if(argument.equals("epargne")){

                        String epar=serveurBanque.getBanque().getNumeroCompteEpargne(cnx.getNumeroCompteClient());
                      //  if(epar!=null){
                            cnx.setNumeroCompteActuel(epar);
                            cnx.envoyer("SELECT OK");
                       // }
                        return;
                    }
                    else{
                        cnx.envoyer("SELECT NO");
                        break;
                    }
                /******************* METHODE DEPOT *******************/
                case"DEPOT":
                    argument=evenement.getArgument();
                    if(cnx.getNumeroCompteClient()==null||!argument.matches("[0-9 ]*")){
                        cnx.envoyer("DEPOT NO");
                        return;
                    }
                    String numComp= cnx.getNumeroCompteActuel();
                    double montant=Double.valueOf(argument);
                    serveurBanque.getBanque().deposer(montant,numComp);
                    cnx.envoyer("DEPOT OUI");
                    break;

                /******************* METHODE RETRAIT *******************/
                case"RETRAIT":
                    if(cnx.getNumeroCompteClient()==null){
                        cnx.envoyer("RETRAIT NO");
                        return;
                    }
                    argument=evenement.getArgument();
                    String numCom= cnx.getNumeroCompteActuel();
                    double monta=Double.valueOf(argument);
                    serveurBanque.getBanque().retirer(monta,numCom);
                    cnx.envoyer("RETRAIT OUI");
                    break;

                /******************* METHODE FACTURE *******************/
                case"FACTURE":
                    argument=evenement.getArgument();
                    t=argument.split(" ",3);
                    if (!t[0].matches("[0-9 ]*")){
                       cnx.envoyer( "FACTURE NON");
                       return;
                    }
                    double facMon=Double.valueOf(t[0]);
                    String numFac=t[1];
                    String desc=t[2];
                    String numComA= cnx.getNumeroCompteActuel();
                    serveurBanque.getBanque().payerFacture(facMon,numComA,numFac,desc);
                    cnx.envoyer("FACTURE OUI");
                    break;

                /******************* METHODE TRANSFER *******************/
                case"TRANSFER":
                    argument=evenement.getArgument();
                    t=argument.split(" ");
                    if (!t[0].matches("[0-9 ]*")){
                        cnx.envoyer( "TRANSFER NON");
                        return;
                    }
                    double mon=Double.valueOf(t[0]);
                    String numCpt=t[1];
                    String numCptAc= cnx.getNumeroCompteActuel();

                    if(!serveurBanque.getBanque().getListeCompteNum().contains(numCpt)){
                        cnx.envoyer( "TRANSFER NON");
                        return;
                    }
                    else
                    serveurBanque.getBanque().transferer(mon,numCptAc,numCpt);
                    cnx.envoyer("TRANSFER OUI");
                    break;

                /******************* HISTORIQUE*******************/
                case"HIST":
                    if(cnx.getNumeroCompteClient()==null){
                        cnx.envoyer("HIST  NO");
                        return;
                    }
                    String hist=serveurBanque.getBanque().getHistoriqueCompteSeelcted(cnx.getNumeroCompteActuel());
                    cnx.envoyer(hist);

                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}