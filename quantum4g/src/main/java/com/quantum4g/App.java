package com.quantum4g;


import com.quantum4g.gui.componentes.VentanaPrincipal;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App 
{
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
          
          VentanaPrincipal ventana=new VentanaPrincipal();
          ventana.setVisible(true);
    }
}
