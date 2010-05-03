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
    private Integer N;
    
    //Lista de los Factores de Bondad por Gen del universo
    private List<Gen> listaGenes;

    //M�todo principal de la ejecuci�n del algoritmo
    public void ejecucionGRASP(){

        inicializarListaFactoresBondad();
        List<Cromosoma> poblacionInicial=new ArrayList<Cromosoma>();

        //Cantidad candidata de iteraciones
        int cantidadIteraciones=100;
        
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

            while(sumaFactorPonderacion< (this.N.intValue()/2)){
             
                indiceSuperior=this.N.intValue()-1;
                indiceInferior=0;
                indiceRCL=(int) (Math.random() * (indiceInferior + alfa * (indiceSuperior - indiceInferior)) + indiceInferior);
                genSeleccionado=listaTemporal.get(indiceRCL);
                cromosoma.activarGen(genSeleccionado);
                sumaFactorPonderacion+=cromosoma.hallaSumaFactorPonderacion();
                listaTemporal.remove(indiceRCL);
            }
            if (sumaFactorPonderacion > (this.N.intValue()/2)){
                cromosoma.apagarGen(indiceRCL);
            }
            poblacionInicial.add(cromosoma);
        }

        eliminarRepetidos();
    }

    private void eliminarRepetidos() {

    }

    private void inicializarListaFactoresBondad() {

    }

    private void ordenarListaFactoresBondad(List<Gen> listaGenes) {

    }

}
