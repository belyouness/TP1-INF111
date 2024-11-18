package com.atoudeft.banque;

import java.util.Date;

public class OperationRetrait extends  Operation{

    private double montant;

    public OperationRetrait(TypeOperation typeOperation, Date date, double montant) {
        super(typeOperation,date);
        this.montant=montant;
    }

    @Override
    public String toString(){
        String str=date+"   "+typeOperation+"   "+montant;

        return str;
    }
}
