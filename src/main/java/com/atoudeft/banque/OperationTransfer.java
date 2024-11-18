package com.atoudeft.banque;

import java.util.Date;

public class OperationTransfer extends Operation {

    private double montant;
    private String compteDest;

    public OperationTransfer(TypeOperation typeOperation, Date date, double montant, String compteDestinataire) {
        super(typeOperation,date);
        this.montant=montant;
        this.compteDest=compteDestinataire;
    }

    @Override
    public String toString(){
        String str=date+"   "+typeOperation+"   "+montant+"   "+compteDest;

        return str;
    }
}
