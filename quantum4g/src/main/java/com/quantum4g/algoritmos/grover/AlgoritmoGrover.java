/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.algoritmos.grover;

import com.quantum4g.core.entidades.EstadoCuantico;
import com.quantum4g.utilitarios.OperacionesMatrices;

/**
 *
 * @author Pepe
 */
public class AlgoritmoGrover {

    //Numero de genes
    private int N;

    //Vector de Estados
    private EstadoCuantico[] vectorEstados;

    private int totalElementos;

    private OperacionesMatrices operacionesMatrices;

    EstadoCuantico[][] matrizGrover;

    public AlgoritmoGrover(int N){
        this.N=N;
        this.totalElementos= (int)(Math.pow(2, N-1));
        this.vectorEstados=new EstadoCuantico[totalElementos];
        this.matrizGrover=new EstadoCuantico[totalElementos][totalElementos];
        this.operacionesMatrices=new OperacionesMatrices(N);
    }

    public void ejecucionGrover(){

        EstadoCuantico[] estadosBaseOraculo;

        //Valor tentativo inicial y aleatorio
        int m = (int) (Math.random()*totalElementos);
        int cantidadIteraciones=(int) (Math.sqrt(totalElementos));
        int indiceResultado = 0;

        //Bucle dentro del cual se ejecutara Grover
        for (int i=0;i<cantidadIteraciones;i++){

            inicializarEstadosBase(vectorEstados,N);

            inicializarOperadorGrover(matrizGrover,vectorEstados);

            for (int j=0;j<cantidadIteraciones;j++){

                estadosBaseOraculo= aplicarOraculo(vectorEstados);

                vectorEstados= getOperacionesMatrices().productoMatrizVector(matrizGrover,estadosBaseOraculo);

            }

            indiceResultado=simularMedicion(vectorEstados,N);

        }

        System.out.println("La solucion esta en "+ indiceResultado);

    }

    private EstadoCuantico[][] adaptaMatriz(double[][] matrizHadamard) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void inicializarEstadosBase(EstadoCuantico[] vectorEstados,int N) {
        double[][] matrizHadamard= getOperacionesMatrices().crearTransfHadamard(N);
        EstadoCuantico[][] matrizHadamardEstados=adaptaMatriz(matrizHadamard);
        vectorEstados= getOperacionesMatrices().productoMatrizVector(matrizHadamardEstados, vectorEstados);
    }

    private void inicializarOperadorGrover(EstadoCuantico[][] matrizGrover, EstadoCuantico[] vectorEstados) {
        EstadoCuantico[] estadoBaseConjugado= getOperacionesMatrices().hallaConjugado(vectorEstados);
        EstadoCuantico[] vectorProducto=getOperacionesMatrices().productoVectorial(vectorEstados,estadoBaseConjugado);
        EstadoCuantico[][] productoInicial=getOperacionesMatrices().producto(2,vectorProducto);
        matrizGrover= getOperacionesMatrices().sumar(productoInicial,getOperacionesMatrices().getMatrizIdentidad());
    }

    private EstadoCuantico[] aplicarOraculo(EstadoCuantico[] vectorEstados) {
        return null;
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
