package com.atoudeft.banque;

import com.atoudeft.banque.TypeOperation;

import java.io.Serializable;
import java.util.Date;

public abstract class Operation implements Serializable {

    protected TypeOperation typeOperation;
    protected Date date;

    public  Operation (TypeOperation typeOperation,Date date){
        this.typeOperation=typeOperation;
    }

}
