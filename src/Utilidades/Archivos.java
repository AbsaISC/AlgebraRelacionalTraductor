/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;


import GUIs.TraductorGUI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Archivos {

    
    public static String getPoneNombre() {
        return poneNombre;
    }

    public static void setPoneNombre(String aPoneNombre) {
        poneNombre = aPoneNombre;
    }
    private String nombreArchivo;
    private static String poneNombre;
    // crea archivo
    
//actualiza el contenido en un archivo
     public void crearArchivoUno(String datos,String nombre) throws IOException {
        try {            
            File guardar = new File(nombre);
            setNombreArchivo(nombre);
            if (guardar != null) {
                FileWriter guardaTexto = new FileWriter(guardar);
                guardaTexto.write(datos);
                guardaTexto.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
     
     
    public String Errores() throws IOException {
        String s = "", cad = "";
        try {
            
                    FileReader archivo = new FileReader("Errores.txt");
                    BufferedReader leer = new BufferedReader(archivo);
                    while ((s = leer.readLine()) != null) {
                        cad += s + "\n";
                    }
                    leer.close();
   
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        return cad;
    }
    
     //metodo para abrir archivos
    

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
