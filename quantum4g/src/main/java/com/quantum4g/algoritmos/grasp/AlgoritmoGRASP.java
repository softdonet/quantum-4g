/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.algoritmos.grasp;

import com.quantum4g.core.entidades.Cromosoma;
import com.quantum4g.core.entidades.Gen;
import com.quantum4g.core.entidades.Triada;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pepe
 */
public class AlgoritmoGRASP {

    //N�mero de Genes
    private int N;

    //Total de Elementos
    private int totalElementos;

    //Lista de los Factores de Bondad por Gen del universo
    private List<Gen> listaGenes;

    //private Cromosoma[] universo;

    private Triada[] listaTriada;

    public AlgoritmoGRASP(int N, Triada[] listaTriada){
        this.N=N;
        this.totalElementos=(int)(Math.pow(2,N));
        this.listaTriada=listaTriada;

    }


    //M�todo principal de la ejecuci�n del algoritmo
    public void ejecucionGRASP(){


        inicializarListaGenes(this.listaGenes,this.listaTriada);

        List<Cromosoma> poblacionInicial=new ArrayList<Cromosoma>();

        //Cantidad candidata de iteraciones
        int cantidadIteraciones=20;
        
        //Valor candidato del factor de relajaci�n
        double alfa=0.3;

        //Declaraci�n de Variables
        int indiceSuperior,indiceInferior,indiceRCL=0;
        double sumaFactorPonderacion;
        Gen genSeleccionado;
        Cromosoma cromosoma;
        List<Gen> listaTemporal;

        ordenarListaFactoresBondad(this.listaGenes);

        for (int i=0;i<cantidadIteraciones;i++){

            sumaFactorPonderacion=0;
            cromosoma=new Cromosoma(N);
            listaTemporal=new ArrayList<Gen>();
            //Creacion de la lista de Genes que sera modificada
            for (int j=0;j<this.listaGenes.size();j++){
                listaTemporal.add(listaGenes.get(j));
            }

            while(sumaFactorPonderacion< (this.N/2)){
             
                indiceSuperior=this.N-1;
                indiceInferior=0;
                indiceRCL=(int) (Math.random() * (indiceInferior + alfa * (indiceSuperior - indiceInferior)) + indiceInferior);
                genSeleccionado=listaTemporal.get(indiceRCL);
                cromosoma.activarGen(genSeleccionado.getNumeroGen(),true);
                sumaFactorPonderacion+=this.listaTriada[indiceRCL].getFactorPonderacion();
                listaTemporal.remove(indiceRCL);
            }
            if (sumaFactorPonderacion > (this.N/2)){
                cromosoma.activarGen(indiceRCL,false);
            }
            poblacionInicial.add(cromosoma);
        }

        poblacionInicial=eliminarRepetidos(poblacionInicial);
    }

    private void ordenarListaFactoresBondad(List<Gen> listaGenes) {
        //BUBBLE SORT-- Se puede optimizar
        Gen temporal;
        for (int i=0;i<listaGenes.size();i++){
                for (int j=0;j<listaGenes.size();j++){
                   if (listaGenes.get(j).getGradoBondad()>listaGenes.get(i).getGradoBondad()){
                      temporal=listaGenes.get(i);
                      listaGenes.set(i, listaGenes.get(j));
                      listaGenes.set(j, temporal);
                   }
                }
            }
    }

    private void inicializarListaGenes(List<Gen> listaGenes, Triada[] listaTriada) {
        double valorBondad;
        for (int i=0;i<listaTriada.length;i++){
            Gen gen=new Gen();
            gen.setNumeroGen(i);
            gen.setTriada(listaTriada[i]);
            valorBondad=(listaTriada[i].getFactorPositivo()+listaTriada[i].getFactorNegativo())/(listaTriada[i].getFactorPonderacion());
            gen.setGradoBondad(valorBondad);
            listaGenes.add(gen);
        }

    }

    private List<Cromosoma> eliminarRepetidos(List<Cromosoma> poblacionInicial) {
        int tamInicial=poblacionInicial.size();
        List<Cromosoma> poblacionFinal=new ArrayList<Cromosoma>();
        for (int i=0;i<tamInicial;i++){
            boolean seRepite=false;
            for (int j=0;j<tamInicial;j++){
                if (i!=j && poblacionInicial.get(i).equals(poblacionInicial.get(j))){
                    seRepite=true;
                }
            }
            if (!seRepite) poblacionFinal.add(poblacionInicial.get(i));
        }
        return poblacionFinal;
    }

}
