/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.core.entidades;

import java.util.List;

/**
 *
 * @author antartec
 */
public class Cromosoma {

    private Double gradoBondadIndividuo;

    private List<Gen> genes;

    public Double getGradoBondadIndividuo() {
        return gradoBondadIndividuo;
    }

    public void setGradoBondadIndividuo(Double gradoBondadIndividuo) {
        this.gradoBondadIndividuo = gradoBondadIndividuo;
    }

    public List<Gen> getGenes() {
        return genes;
    }

    public void setGenes(List<Gen> genes) {
        this.genes = genes;
    }


}
