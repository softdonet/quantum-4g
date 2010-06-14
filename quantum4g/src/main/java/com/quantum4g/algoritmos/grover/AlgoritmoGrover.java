/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.algoritmos.grover;

import com.quantum4g.core.entidades.EstadoCuantico;
import com.quantum4g.utilitarios.OperacionesMatrices;
import com.quantum4g.core.entidades.Cromosoma;
/**
 *
 * @author Pepe
 */
public class AlgoritmoGrover {

    //Numero de genes
    private int N;

    //Vector de Estados
    //private EstadoCuantico[] vectorEstados;

    private int totalElementos;

    private OperacionesMatrices operacionesMatrices;
    
    private Cromosoma[] universo;

    public AlgoritmoGrover(int N,Cromosoma[] universo){
        this.N=N;
        this.totalElementos= (int)(Math.pow(2, N));
        this.operacionesMatrices=new OperacionesMatrices(N);
        this.universo=universo;
    }

    public void ejecucionGrover(){

        EstadoCuantico[] vectorEstados=new EstadoCuantico[totalElementos];
        EstadoCuantico[][] matrizGrover=null;

        //Valor tentativo inicial y aleatorio
        int m = (int) (Math.random()*totalElementos);
        int cantidadIteraciones=(int) (Math.sqrt(totalElementos));
        int indiceResultado = 0;

        //Bucle dentro del cual se ejecutara Grover
        for (int i=0;i<cantidadIteraciones;i++){
            inicializarEstadosBase(vectorEstados,N);
            //if (true) return;
            matrizGrover=inicializarOperadorGrover(vectorEstados);
            for (int j=0;j<cantidadIteraciones;j++){
                m=aplicarOraculo(vectorEstados,m);
                getOperacionesMatrices().productoMatrizVector(matrizGrover,vectorEstados);
            }
            indiceResultado=simularMedicion(vectorEstados,N);
        }
        System.out.println("La solucion esta en "+ indiceResultado + " con valor de fitness total:" +
                universo[indiceResultado].getGradoBondadIndividuo());
    }

    private EstadoCuantico[][] adaptaMatriz(double[][] matrizHadamard) {
        return null;
    }

    private void inicializarEstadosBase(EstadoCuantico[] vectorEstados,int N) {
        double probabilidad=1/Math.sqrt(totalElementos);
        //TODO: Inicializar con la informacion de valor de fitness
        for (int i=0;i<totalElementos;i++){
            vectorEstados[i]=new EstadoCuantico();
            vectorEstados[i].setNumeroEstado(i);
            vectorEstados[i].setValorMatriz(probabilidad);
            vectorEstados[i].setAmplitudProbabilidad(probabilidad);
            vectorEstados[i].setValorFitness(universo[i].getGradoBondadIndividuo());
            vectorEstados[i].setSumaFactorPonderacion(universo[i].getSumaFactorPonderacion());
        }
        /*double[][] matrizHadamard= getOperacionesMatrices().crearTransfHadamard(N);
        EstadoCuantico[][] matrizHadamardEstados=adaptaMatriz(matrizHadamard);
        vectorEstados= getOperacionesMatrices().productoMatrizVector(matrizHadamardEstados, vectorEstados);
         * */
    }

    private EstadoCuantico[][] inicializarOperadorGrover(EstadoCuantico[] vectorEstados) {
        EstadoCuantico[] estadoBaseConjugado= getOperacionesMatrices().hallaDual(vectorEstados);
        EstadoCuantico[][] matrizProducto=getOperacionesMatrices().productoVectorial(vectorEstados,estadoBaseConjugado);
        getOperacionesMatrices().productoConEscalar(2,matrizProducto);
        return getOperacionesMatrices().restar(matrizProducto,getOperacionesMatrices().getMatrizIdentidad());
    }

    private int aplicarOraculo(EstadoCuantico[] vectorEstados, int indice) {
        int indiceOraculo=(int) (Math.random() * this.totalElementos);
        int contador=0;
        while(true){
            if (indiceOraculo!=indice &&
                vectorEstados[indiceOraculo].getValorFitness()>vectorEstados[indice].getValorFitness()&&
                vectorEstados[indiceOraculo].getSumaFactorPonderacion()<this.N*2/5) {
                break;
            }
            else{
                if (contador>200) break;
                indiceOraculo=(int) (Math.random() * this.totalElementos);
            }
            contador++;
        }
        vectorEstados[indiceOraculo].setAmplitudProbabilidad(vectorEstados[indiceOraculo].getAmplitudProbabilidad()*-1);
        vectorEstados[indiceOraculo].setValorMatriz(vectorEstados[indiceOraculo].getValorMatriz()*-1);
        return indiceOraculo;
    }

    private int simularMedicion(EstadoCuantico[] vectorEstados, int N) {
        double mayorProbabilidad=0;
        int indiceProbabilidad=0;

        for (int i=0;i<N;i++){
            if (vectorEstados[i].getAmplitudProbabilidad()>mayorProbabilidad){
                mayorProbabilidad=vectorEstados[i].getAmplitudProbabilidad();
                indiceProbabilidad=i;
            }
        }
        return indiceProbabilidad;
    }

    public OperacionesMatrices getOperacionesMatrices() {
        return operacionesMatrices;
    }

    public void setOperacionesMatrices(OperacionesMatrices operacionesMatrices) {
        this.operacionesMatrices = operacionesMatrices;
    }



}
