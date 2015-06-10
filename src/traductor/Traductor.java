/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traductor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Absa
 */
public class Traductor {

    /**
     * @param args the command line arguments
     */
    public final static int GENERAR = 1;
    public final static int EJECUTAR = 2;
    public final static int SALIR = 3;

    /**
     * Es un menu para elegir entre generar el analizador lexico y sintactico, o
     * ejecutarlos sobre un archivo de pruebas.
     *
     * @param args the command line arguments
     */
    public  void main(String[] args) {
        java.util.Scanner in = new Scanner(System.in);
        int valor = 0;
        do {
            System.out.println("Elija una opcion: ");
            System.out.println("1) Generar");
            System.out.println("2) Ejecutar");
            System.out.println("3) Salir");
            System.out.print("Opcion: ");
            valor = in.nextInt();
            switch (valor) {
                case GENERAR: {
                    System.out.println("\n*** Generando ***\n");
                    String archLexico = "";
                    String archSintactico = "";
                    if (args.length > 0) {
                        System.out.println("*** Procesando archivos de entradas ***");
                        archLexico = args[0];
                        archSintactico = args[1];
                    } else {
                        System.out.println("*** Procesando archivos defaults ***");
                        archLexico = "AnLexico.flex";
                        archSintactico = "AnSintactico.cup";
                    }
                    String[] alexico = {archLexico};
                    String[] asintactico = {"-parser", "AnalizadorSintactico", archSintactico};
                    
                    try {
                        jflex.Main.main(alexico);
                        java_cup.Main.main(asintactico);
                    } catch (Exception ex) {
                        Logger.getLogger(Traductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //movemos los archivos generados
                    System.out.println("*******MOVIENDO ARCHIVOS********");
                    System.out.println("AnalizadorLexico\nAnalizadorSintactico\nsym");
                    boolean mvAL=false,mvAS=false,mvSym=false;
                    
                        if(!mvAL)
                         mvAL = moverArch("AnalizadorLexico.java");
                        if(!mvAS)
                         mvAS = moverArch("AnalizadorSintactico.java");
                        if(!mvSym)
                          mvSym = moverArch("sym.java");
                    if (mvAL && mvAS && mvSym) {
                        System.out.println("Se movio con exito.");
                    }
                    break;
                }
                case EJECUTAR: {
                    /*  Ejecutamos el analizador lexico y sintactico
                     sobre un archivo de pruebas. 
                     */
                    String[] archivoPrueba = {"test.txt"};
                    //AnalizadorSintactico.main(archivoPrueba);
                    System.out.println("Ejecutado!");
                    break;
                }
                case SALIR: {
                    System.out.println("Adios!");
                    break;
                }
                default: {
                    System.out.println("Opcion no valida!");
                    break;
                }
            }
        } while (valor != 3);

    }
     public static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            Path currentRelativePath = Paths.get("");
            String nuevoDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "traductor"
                    + "" + File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                efectuado = true;
            } else {
            }
        } else {
        }
        return efectuado;
    }
}
