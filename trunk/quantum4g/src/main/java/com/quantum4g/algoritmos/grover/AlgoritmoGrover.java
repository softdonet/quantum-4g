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

    //Cantidad de operaciones cuanticas
    private int cantidadOperaciones;

    private int totalElementos;

    private OperacionesMatrices operacionesMatrices;
    
    private Cromosoma[] universo;

    public AlgoritmoGrover(int N,Cromosoma[] universo){
        this.N=N;
        this.totalElementos= (int)(Math.pow(2, N));
        this.operacionesMatrices=new OperacionesMatrices(N);
        this.universo=universo;
        this.cantidadOperaciones=0;
    }

    public double ejecucionGrover(){

        EstadoCuantico[] vectorEstadosInicial=new EstadoCuantico[totalElementos];
        EstadoCuantico[] vectorEstadosActual=new EstadoCuantico[totalElementos];
        EstadoCuantico[] vectorEstadosOraculo= null;
        //EstadoCuantico[][] matrizGrover=null;

        //Valor tentativo inicial y aleatorio
        int m = (int) (Math.random()*totalElementos);
        this.cantidadOperaciones++;
        int cantidadIteraciones=(int) (Math.sqrt(totalElementos));
        int indiceResultado = 0;

        
        inicializarEstadosBase(vectorEstadosInicial,N);
        this.cantidadOperaciones+=(int)Math.log(this.totalElementos);
        //Bucle dentro del cual se ejecutara Grover
        for (int i=0;i<cantidadIteraciones;i++){
            inicializarEstadosBase(vectorEstadosActual,N);
            this.cantidadOperaciones+=(int)Math.log(this.totalElementos);
            for (int j=0;j<cantidadIteraciones;j++){
                double coeficiente=0;
                /*
                 * TODO:
                 * Falta revisar si el ORACULO siempre se aplicara a un solo elemento o
                 * ira eligiendo aleatoriamente
                 * POR EL MOMENTO ALEATORIAMENTE.
                 */
                vectorEstadosOraculo=aplicarOraculo(vectorEstadosActual,m);
                this.cantidadOperaciones++;
                coeficiente=2*getOperacionesMatrices().productoPuntoVectores(vectorEstadosInicial, vectorEstadosOraculo);
                getOperacionesMatrices().productoVectorConEscalar(coeficiente, vectorEstadosInicial,vectorEstadosActual);
                getOperacionesMatrices().restaVectores(vectorEstadosActual, vectorEstadosOraculo);
            }
            m=simularMedicion(vectorEstadosActual);
            //Multiplicar por la probabilidad
            this.cantidadOperaciones++;
        }
        //System.out.println("La solucion GROVER esta en "+ m + " con valor de fitness total:" +
        //        universo[m].getGradoBondadIndividuo());

        return universo[m].getGradoBondadIndividuo()*0.80;
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


    private EstadoCuantico[] aplicarOraculo(EstadoCuantico[] vectorEstados, int indice) {
        
        EstadoCuantico[] vectorOraculo=new EstadoCuantico[vectorEstados.length];
        /*Se debe copiar cada atributo, porque al ser los objetos por referencia, al igualar, solo 
         * se igualaria la referencia y al modificar un valor, se modificarian ambos
         */
        for (int i=0;i<vectorEstados.length;i++){
            vectorOraculo[i]=new EstadoCuantico();
            vectorOraculo[i].setAmplitudProbabilidad(vectorEstados[i].getAmplitudProbabilidad());
            vectorOraculo[i].setConDesfaseXOraculo(vectorEstados[i].getConDesfaseXOraculo());
            vectorOraculo[i].setExiste(vectorEstados[i].getExiste());
            vectorOraculo[i].setNumeroEstado(vectorEstados[i].getNumeroEstado());
            vectorOraculo[i].setSumaFactorPonderacion(vectorEstados[i].getSumaFactorPonderacion());
            vectorOraculo[i].setValorFitness(vectorEstados[i].getValorFitness());
            vectorOraculo[i].setValorMatriz(vectorEstados[i].getValorMatriz());
        }

        int indiceOraculo=(int) (Math.random() * this.totalElementos);
        int contador=0;
        while(true){
            if (indiceOraculo!=indice &&
                vectorEstados[indiceOraculo].getValorFitness()>vectorEstados[indice].getValorFitness()&&
                vectorEstados[indiceOraculo].getSumaFactorPonderacion()<this.N/2) {
                break;
            }
            else{
                if (contador>10) {
                    indiceOraculo=indice;
                    break;
                }
                indiceOraculo=(int) (Math.random() * this.totalElementos);
            }
            contador++;
        }
        vectorOraculo[indiceOraculo].setAmplitudProbabilidad(vectorOraculo[indiceOraculo].getAmplitudProbabilidad()*-1);
        vectorOraculo[indiceOraculo].setValorMatriz(vectorOraculo[indiceOraculo].getValorMatriz()*-1);
        return vectorOraculo;
    }

    private int simularMedicion(EstadoCuantico[] vectorEstados) {
        double mayorProbabilidad=0;
        int indiceProbabilidad=0;

        for (int i=0;i<vectorEstados.length;i++){
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

    public int getCantidadOperaciones() {
        return cantidadOperaciones;
    }

    public void setCantidadOperaciones(int cantidadOperaciones) {
        this.cantidadOperaciones = cantidadOperaciones;
    }



}
