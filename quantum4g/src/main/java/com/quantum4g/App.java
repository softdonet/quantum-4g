package com.quantum4g;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;
import com.quantum4g.gui.ventanas.VentanaPrincipal;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class App 
{
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
          
          VentanaPrincipal ventana=new VentanaPrincipal();
          ventana.setVisible(true);
          //ventana.ejecutarExperimentoPrincipal();
          //TODO- Medir la eficiencia
    }
}
