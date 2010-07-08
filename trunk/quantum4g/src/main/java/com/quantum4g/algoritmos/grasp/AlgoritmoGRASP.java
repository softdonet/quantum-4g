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

        //System.out.println("Antes de inicializar Lista de Genes");
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

        //System.out.println("Antes de ordenar  la lista por factores de bondad");
        ordenarListaFactoresBondad(this.listaGenes);

        this.cantidadOperaciones+=5*this.listaTriada.length;

        for (int i=0;i<cantidadIteraciones;i++){

            //System.out.println("Iteracion "+i);
            sumaFactorPonderacion=0;
            cromosoma=new Cromosoma(N);
            cromosoma.inicializarGenes();
            this.cantidadOperaciones++;
            listaTemporal=new ArrayList<Gen>();
            //Creacion de la lista de Genes que sera modificada
            for (int j=0;j<this.listaGenes.size();j++){
                listaTemporal.add(listaGenes.get(j));
            }
            //IMPORTANTE, cambiado de N/2 a N/4
            while(sumaFactorPonderacion< (this.N/2) && listaTemporal.size()>0){

                //System.out.println("Iteracion secundaria de "+ i );
                indiceSuperior=listaTemporal.size()-1;
                indiceInferior=0;
                this.cantidadOperaciones++;
                indiceRCL=(int) (Math.random() * (indiceInferior + alfa * (indiceSuperior - indiceInferior)) + indiceInferior);
                this.cantidadOperaciones++;
                genSeleccionado=listaTemporal.get(indiceRCL);
                this.cantidadOperaciones++;
                //System.out.println("Antes de activacion de Gen");
                cromosoma.activarGen(genSeleccionado.getNumeroGen(),true);
                this.cantidadOperaciones++;
                cromosoma.getGenes().get(genSeleccionado.getNumeroGen()).setGradoBondad(genSeleccionado.getGradoBondad());
                this.cantidadOperaciones++;
                sumaFactorPonderacion+=genSeleccionado.getTriada().getFactorPonderacion();
                this.cantidadOperaciones++;
                //System.out.println("Antes de eliminar el gen del indice seleccionado");
                listaTemporal.remove(indiceRCL);
                this.cantidadOperaciones++;
            }
            if (sumaFactorPonderacion > (this.N*2/5)){
                cromosoma.activarGen(indiceRCL,false);
                this.cantidadOperaciones++;
            }
            //System.out.println("Despues de a�adir el "+ i+ "-esimo cromosoma");
            cromosoma.hallaFactorBondadIndividuo();
            this.cantidadOperaciones++;
            poblacionInicial.add(cromosoma);
            this.cantidadOperaciones++;
        }
        //System.out.println("Antes de la eliminacion de Repetidos");
        poblacionInicial=eliminarRepetidos(poblacionInicial);
        this.cantidadOperaciones++;
        /*Impresion de resultados*/

        //DEBUGGING
        double valorResultadoGRASP=-1;
        for (int i=0;i<poblacionInicial.size();i++)
        {
            //System.out.print("Solucion GRASP "+i+" es: ");
            double valorFitness=0;
            for (int j=0;j<poblacionInicial.get(i).getGenes().size();j++){
            //    System.out.print(poblacionInicial.get(i).getGenes().get(j).getValor()?1:0);
            }
            //System.out.print(" con valor de fitness " + poblacionInicial.get(i).getGradoBondadIndividuo());
            //System.out.println();
        }
        //DEBUGGING
        if (poblacionInicial.size()>0 && poblacionInicial.get(0)!=null){
            valorResultadoGRASP=poblacionInicial.get(0).getGradoBondadIndividuo();
        }
        return valorResultadoGRASP;

    }

    private void ordenarListaFactoresBondad(List<Gen> listaGenes) {
        //BUBBLE SORT-- Se puede optimizar
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