package com.atoudeft.banque;

import java.util.Date;

public class OperationFacture extends  Operation{

    private double montant;
    private String numeroFac;
    private String description;

    public OperationFacture(TypeOperation typeOperation, Date date, double montant, String numeroFacture, String description) {
        super(typeOperation,date);
        this.numeroFac=numeroFacture;
        this.montant=montant;
        this.description=description;
    }

    @Override
    public String toString(){
        String str=date+"   "+typeOperation+"   "+montant+"   "+numeroFac+"   "+description;

        return str;
    }
}
