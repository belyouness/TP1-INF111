package com.atoudeft.banque;

import java.io.Serializable;

public class PileChainee implements Serializable {


        private Object[] donnees;  //les données de la file
        private int sommet; 		//indice de l'élément au sommet de la file

        private int nbElement; 	//Nombre d'éléments dans la pile (utilisé pour
        //ne pas avoir à recalculer le nombre d'éléments dans
        //la méthode taille()). Ne pas oublier de l'incrémenter
        //lorsqu'on empile et le décrémenter lorsqu'on dépile.

        /**
         * Constructeur sans paramètre
         * Crée une pile avec une capacité de 10.
         */
        public PileChainee(){
            this.donnees=new Object[30];
            this.sommet=-1;
            this.nbElement=0;
        }

        /**
         * Crée une pile de la taille demandée.
         * @param taille La taille voulue pour la file.
         */
        public PileChainee(int taille){
            this.donnees=new Object[taille];
            this.sommet=-1;
            this.nbElement=0;
        }

        /**
         * Ajoute un élément au sommet de la pile.
         *
         * @param element l'élément à empiler.
         * @return true si l'opération réussit et false sinon (pile pleine)
         */
        public boolean empiler(Object element) {
            if(taille()== donnees.length){return false;}
            sommet++;
            donnees[sommet]=element;
            nbElement++;
            return true;
        }

        /**
         * Retire l'élément au sommet de la pile.
         *
         * @return L'élément au sommet de la pile s'il existe ou null sinon.
         */
        public Object depiler(){
            if(estVide()){
                return null;
            }
            else
            {
                Object x=donnees[sommet];
                donnees[sommet]=null;
                sommet--;
                nbElement--;
                return x;
            }
        }

        /**
         * Indique si la pile est vide.
         *
         * @return true si la  pile est vide et false sinon.
         */
        public boolean estVide(){
            return sommet<0;
        }

        /**
         * Vide la pile.
         */
        public void vider(){
            //Indication : utiliser une boucle while pour depiler la pile aussi
            //longtemps qu'elle n'est pas vide (2 lignes de code).
            while (!estVide()){depiler();}
        }

        /**
         * Permet de consulter l'élément au sommet de la pile sans l'enlever.
         *
         * @return L'élément au sommet si la pile n'est pas vide et null sinon.
         */
        public Object peek(){
            if (estVide()){
                return null;
            }
            return donnees[sommet];
        }


        /**
         * Retourne le nombre d'éléments dans la pile.
         *
         * @return Le nombre d'éléments actuellement dans la pile.
         */
        public int taille(){

            return this.nbElement;
        }

}
