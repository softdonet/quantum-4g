/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.experimentos.principal;

import com.quantum4g.core.entidades.Cromosoma;
import com.quantum4g.core.entidades.Gen;
import com.quantum4g.core.entidades.Triada;

/**
 *
 * @author Pepe
 */
public class Principal {

    private int N;
    private int totalElementos;

    public Principal(int N){
        this.N=N;
        this.totalElementos=(int) Math.pow(2,N);
    }


    public Triada[] generaValoresTriadaGen(int N){
        Triada[] triada=new Triada[N];
        for (int l=0;l<N;l++){
            triada[l]=new Triada();
            triada[l].setFactorPositivo(Math.random()*100);
            triada[l].setFactorNegativo(Math.random()*100*-1);
            triada[l].setFactorPonderacion(Math.random());
        }
        return triada;
    }


    public Cromosoma[] inicializaUniverso(){
        Cromosoma[] universo= new Cromosoma[totalElementos];
        int valorEntero;
        boolean valorLogico;
        Gen gen=null;

        Triada[] triada=generaValoresTriadaGen(N);

        for (int i=0;i<totalElementos;i++){
            universo[i]=new Cromosoma(N);            
            for (int j=0;j<N;j++){
                gen=new Gen();
                valorEntero=(i & (int)(Math.pow(2,j)))>>j;
                if (valorEntero==1){
                    valorLogico=true;
                }
                else{
                    valorLogico=false;
                }
                gen.setValor(valorLogico);
                gen.setGradoBondad((triada[j].getFactorPositivo()+triada[j].getFactorNegativo())*triada[j].getFactorPonderacion());
                universo[i].getGenes().add(gen);
            }
            universo[i].hallaFactorBondadIndividuo();
        }
        return universo;
    }

}
