package com.atoudeft.banque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompteClient implements Serializable {
    protected String numero; /** protected pour pouvoir acceder a la valeur dans la verification des comptes si existe deja**/
    private String nip;
    protected List<CompteBancaire> comptes; /** prot pour pouvoir get compte-cheque num dans banque getnumdefaut **/

    /**
     * Crée un compte-client avec un numéro et un nip.
     *
     * @param numero le numéro du compte-client
     * @param nip le nip
     */
    public CompteClient(String numero, String nip) {
        this.numero = numero;
        this.nip = nip;
        comptes = new ArrayList<>();
    }
    /** Necessaire pour method CONNECT (verifier utilisateur) **/
    public String getNip() {
        return nip;
    }

    /** CONNECT  pour recuperer num compte cheque pour set cnx **/
    public List<CompteBancaire> getComptes() {
        return comptes;
    }

    /**
     * Ajoute un compte bancaire au compte-client.
     *
     * @param compte le compte bancaire
     * @return true si l'ajout est réussi
     */
    public boolean ajouter(CompteBancaire compte) {
        return this.comptes.add(compte);
    }
}