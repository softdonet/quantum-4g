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

    //Cantidad de operaciones clasicas
    private int cantidadOperaciones;

    //Lista de los Factores de Bondad por Gen del universo
    private List<Gen> listaGenes;

    //private Cromosoma[] universo;

    private Triada[] listaTriada;

    public AlgoritmoGRASP(int N, Triada[] listaTriada){
        this.N=N;
        this.totalElementos=(int)(Math.pow(2,N));
        this.listaTriada=listaTriada;
        this.listaGenes=new ArrayList<Gen>();
        this.cantidadOperaciones=0;
    }
    
    //Metodo principal de la ejecucion del algoritmo
    public double ejecucionGRASP(){

        inicializarListaGenes(this.listaGenes,this.listaTriada);

        List<Cromosoma> poblacionInicial=new ArrayList<Cromosoma>();

        //Cantidad candidata de iteraciones
        int cantidadIteraciones=20;
        
        //Valor candidato del factor de relajaci�n
        double alfa=0.3;

        //Declaracion de Variables
        int indiceSuperior,indiceInferior,indiceRCL=0;
        double sumaFactorPonderacion;
        Gen genSeleccionado;
        Cromosoma cromosoma;
        List<Gen> listaTemporal;

        //ordenarListaFactoresBondad(this.listaGenes);
        ordenarListaFactoresBondadQuicksort(listaGenes, 0, listaGenes.size()-1);
        //Complejidad n*log(n) promedio de quicksort
        this.cantidadOperaciones+=listaGenes.size()*(int)Math.log(listaGenes.size());

        for (int i=0;i<cantidadIteraciones;i++){

            sumaFactorPonderacion=0;
            cromosoma=new Cromosoma(N);
            cromosoma.inicializarGenes();
            this.cantidadOperaciones++;
            listaTemporal=new ArrayList<Gen>();
            //Creacion de la lista de Genes que sera modificada
            for (int j=0;j<this.listaGenes.size();j++){
                listaTemporal.add(listaGenes.get(j));
            }
            while(sumaFactorPonderacion< (this.N/2) && listaTemporal.size()>0){

                indiceSuperior=listaTemporal.size()-1;
                this.cantidadOperaciones++;
                indiceInferior=0;
                indiceRCL=(int) (Math.random() * (indiceInferior + alfa * (indiceSuperior - indiceInferior)) + indiceInferior);
                this.cantidadOperaciones++;
                genSeleccionado=listaTemporal.get(indiceRCL);
                this.cantidadOperaciones++;
                cromosoma.activarGen(genSeleccionado.getNumeroGen(),true);
                this.cantidadOperaciones++;
                cromosoma.getGenes().get(genSeleccionado.getNumeroGen()).setGradoBondad(genSeleccionado.getGradoBondad());
                this.cantidadOperaciones++;
                sumaFactorPonderacion+=genSeleccionado.getTriada().getFactorPonderacion();
                this.cantidadOperaciones++;
                listaTemporal.remove(indiceRCL);
                this.cantidadOperaciones++;
            }
            if (sumaFactorPonderacion > (this.N*2/5)){
                cromosoma.activarGen(indiceRCL,false);
                this.cantidadOperaciones++;
            }
            cromosoma.hallaFactorBondadIndividuo();
            this.cantidadOperaciones++;
            poblacionInicial.add(cromosoma);
            this.cantidadOperaciones++;
        }
        poblacionInicial=eliminarRepetidos(poblacionInicial);
        this.cantidadOperaciones++;
        
        double valorResultadoGRASP=-1;
        if (poblacionInicial.size()>0 && poblacionInicial.get(0)!=null){
            valorResultadoGRASP=poblacionInicial.get(0).getGradoBondadIndividuo();
        }
        return valorResultadoGRASP;
    }

    //Ordenamiento usando Bubblesort
    private void ordenarListaFactoresBondad(List<Gen> listaGenes) {
        Gen temporal;
        for (int i=0;i<listaGenes.size();i++){
                for (int j=0;j<listaGenes.size();j++){
                   if (listaGenes.get(j).getGradoBondad()<listaGenes.get(i).getGradoBondad()){
                      temporal=listaGenes.get(i);
                      listaGenes.set(i, listaGenes.get(j));
                      listaGenes.set(j, temporal);
                   }
                }
            }
    }

    private void ordenarListaFactoresBondadQuicksort(List<Gen> listaGenes,int primero,int ultimo){
        int i=primero, j=ultimo;
        double pivote=listaGenes.get((primero+ultimo)/2).getGradoBondad();
        Gen auxiliar;
        do{
            while (listaGenes.get(i).getGradoBondad()>pivote) i++;
            while (listaGenes.get(j).getGradoBondad()<pivote) j--;

            if (i<=j){
                auxiliar=listaGenes.get(j);
                listaGenes.set(j, listaGenes.get(i));
                listaGenes.set(i,auxiliar);
                i++;
                j--;
            }
        }while (i<=j);

        if (primero<j) ordenarListaFactoresBondadQuicksort(listaGenes,primero,j);
        if (ultimo>i) ordenarListaFactoresBondadQuicksort(listaGenes, i, ultimo);
    }

    private void inicializarListaGenes(List<Gen> listaGenes, Triada[] listaTriada) {
        double valorBondad;
        for (int i=0;i<listaTriada.length;i++){
            Gen gen=new Gen();
            gen.setNumeroGen(i);
            gen.setTriada(listaTriada[i]);
            valorBondad=(listaTriada[i].getFactorPositivo()+listaTriada[i].getFactorNegativo())*(listaTriada[i].getFactorPonderacion());
            gen.setGradoBondad(valorBondad);
            listaGenes.add(gen);
        }

    }

    private List<Cromosoma> eliminarRepetidos(List<Cromosoma> poblacionInicial) {
        int tamInicial=poblacionInicial.size();
        List<Cromosoma> poblacionFinal=new ArrayList<Cromosoma>();
        for (Cromosoma cromosomaI:poblacionInicial){
            boolean seRepite=false;
            for (Cromosoma cromosomaF:poblacionFinal){
                int contador=0;
                for (int k=0;k<this.N;k++){
                    if (cromosomaI.getGenes().get(k).getValor().equals(cromosomaF.getGenes().get(k).getValor())){
                        contador++;
                    }
                }
                if (contador==N){
                    seRepite=true;
                }
            }
            if (!seRepite) poblacionFinal.add(cromosomaI);
        }
        return poblacionFinal;
    }

    public int getCantidadOperaciones() {
        return cantidadOperaciones;
    }

    public void setCantidadOperaciones(int cantidadOperaciones) {
        this.cantidadOperaciones = cantidadOperaciones;
    }

}
