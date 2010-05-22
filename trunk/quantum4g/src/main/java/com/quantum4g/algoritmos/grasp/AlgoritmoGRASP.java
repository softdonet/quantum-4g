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

    //Nï¿½mero de Genes
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
        this.listaGenes=new ArrayList<Gen>();
    }
    //Mï¿½todo principal de la ejecuciï¿½n del algoritmo
    public void ejecucionGRASP(){

        System.out.println("Antes de inicializar Lista de Genes");
        inicializarListaGenes(this.listaGenes,this.listaTriada);

        List<Cromosoma> poblacionInicial=new ArrayList<Cromosoma>();

        //Cantidad candidata de iteraciones
        int cantidadIteraciones=20;
        
        //Valor candidato del factor de relajaciï¿½n
        double alfa=0.3;

        //Declaraciï¿½n de Variables
        int indiceSuperior,indiceInferior,indiceRCL=0;
        double sumaFactorPonderacion;
        Gen genSeleccionado;
        Cromosoma cromosoma;
        List<Gen> listaTemporal;

        System.out.println("Antes de ordenar  la lista por factores de bondad");
        ordenarListaFactoresBondad(this.listaGenes);

        for (int i=0;i<cantidadIteraciones;i++){

            System.out.println("Iteracion "+i);
            sumaFactorPonderacion=0;
            cromosoma=new Cromosoma(N);
            cromosoma.inicializarGenes();
            listaTemporal=new ArrayList<Gen>();
            //Creacion de la lista de Genes que sera modificada
            for (int j=0;j<this.listaGenes.size();j++){
                listaTemporal.add(listaGenes.get(j));
            }
            //IMPORTANTE, cambiado de N/2 a N/4
            while(sumaFactorPonderacion< (this.N*2/5) && listaTemporal.size()>0){

                System.out.println("Iteracion secundaria de "+ i );
                indiceSuperior=listaTemporal.size()-1;
                indiceInferior=0;
                indiceRCL=(int) (Math.random() * (indiceInferior + alfa * (indiceSuperior - indiceInferior)) + indiceInferior);
                genSeleccionado=listaTemporal.get(indiceRCL);
                System.out.println("Antes de activacion de Gen");
                cromosoma.activarGen(genSeleccionado.getNumeroGen(),true);
                sumaFactorPonderacion+=genSeleccionado.getTriada().getFactorPonderacion();
                System.out.println("Antes de eliminar el gen del indice seleccionado");
                listaTemporal.remove(indiceRCL);
            }
            if (sumaFactorPonderacion > (this.N*2/5)){
                cromosoma.activarGen(indiceRCL,false);
            }
            System.out.println("Despues de añadir el "+ i+ "-esimo cromosoma");
            poblacionInicial.add(cromosoma);
        }
        System.out.println("Antes de la eliminacion de Repetidos");
        poblacionInicial=eliminarRepetidos(poblacionInicial);

        /*Impresion de resultados*/

        for (int i=0;i<poblacionInicial.size();i++)
        {
            System.out.print("Grado de bondad del cromosoma "+i+" es: ");
            for (int j=0;j<poblacionInicial.get(i).getGenes().size();j++){
                System.out.print(poblacionInicial.get(i).getGenes().get(j).getValor()?1:0);
            }
            System.out.println();
        }


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
            valorBondad=(listaTriada[i].getFactorPositivo()+listaTriada[i].getFactorNegativo())/(listaTriada[i].getFactorPonderacion());
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

}
