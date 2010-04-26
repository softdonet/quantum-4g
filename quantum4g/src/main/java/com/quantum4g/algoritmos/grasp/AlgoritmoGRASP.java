/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.algoritmos.grasp;

import com.quantum4g.core.entidades.Cromosoma;
import com.quantum4g.core.entidades.Triada;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pepe
 */
public class AlgoritmoGRASP {

    //Número de Genes
    private Integer N;
    
    //Lista de los Factores de Bondad por Gen del universo
    private List<Cromosoma> universo;

    //Método principal de la ejecución del algoritmo
    public void ejecucionGRASP(){

        inicializarListaFactoresBondad();
        List<Cromosoma> poblacionInicial=new ArrayList<Cromosoma>();

        //Cantidad candidata de iteraciones
        int cantidadIteraciones=100;
        
        //Valor candidato del factor de relajación
        double alfa=0.3;

        //Declaración de Variables
        int indiceSuperior,indiceInferior,indiceRCL=0;
        double sumaFactorPonderacion;
        Cromosoma cromosoma;

        for (int i=0;i<cantidadIteraciones;i++){

            sumaFactorPonderacion=0;
            cromosoma=new Cromosoma();

            while(sumaFactorPonderacion< (this.N.intValue()/2)){

                ordenarListaFactoresBondad(this.universo);
                indiceSuperior=this.N.intValue()-1;
                indiceInferior=0;
                indiceRCL=(int) (Math.random() * (indiceInferior + alfa * (indiceSuperior - indiceInferior)) + indiceInferior);
                cromosoma=universo.get(indiceRCL);
                poblacionInicial.add(cromosoma);
                universo.remove(cromosoma);
                sumaFactorPonderacion+=cromosoma.hallaSumaFactorPonderacion();
               
            }

            if (sumaFactorPonderacion > (this.N.intValue()/2)){
                poblacionInicial.remove(indiceRCL);
            }
        }

        eliminarRepetidos();
    }

    private void eliminarRepetidos() {

    }

    private void inicializarListaFactoresBondad() {

    }

    private void ordenarListaFactoresBondad(List<Cromosoma> universo) {

    }

}
