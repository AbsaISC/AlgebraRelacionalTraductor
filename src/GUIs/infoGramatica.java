/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

/**
 *
 * @author Absa
 */
public class infoGramatica extends javax.swing.JFrame {

    /**
     * Creates new form infoGramatica
     */
    public infoGramatica() {
        initComponents();
        this.jTextArea1.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("R ::= \t    TABLA\n\t  | R CRUZ R \n\t  | R UNION R\n\t  | SELECT PAR_I Condicion COMA R PAR_D\n\t  | PROY PAR_I  ListaCampos COMA R PAR_D\n\t  | R INTERCECTION R PAR_I ListaCampos PAR_D\n\t  | R DIFERENCIA R PAR_I Condicion PAR_D \n\t  | JOIN PAR_I R COMA R PAR_D  \n\t  | JOIN PAR_I Condicion COMA R COMA R PAR_D\n\t  | PAR_I R PAR_D\n\t  ;\n\nListaCampos ::= \tCampo\n\t\t      | PAR_I ListaCampos PAR_D \n\t\t      | ListaCampos COMA Campo\n\t\t       ;\n\nCampo ::=   IDENTIFICADOR PUNTO IDENTIFICADOR\n\t  | IDENTIFICADOR\n\t  ;\n\nCondicion ::=   Expresion\n\t      | Condicion OR Condicion\n\t      | Condicion AND Condicion\n\t      | NOT Expresion\n       \t      ;\n\nExpresion ::= PAR_I Expresion\n\t\t\t  | Expresion MAYOR Expresion\n\t\t\t  | Expresion MAYORIGUAL Expresion\n\t\t\t  | Expresion MENOR Expresion\n\t\t\t  | Expresion MENORIGUAL Expresion\n\t\t\t  | Expresion IGUAL Expresion\n\t\t\t  | Expresion DIFERENTE Expresion\n\t\t\t  | Expresion COMO CADENA\n\t\t\t  | CADENA\n\t\t\t  | NUMERO\n\t\t\t  | Campo\n\t\t\t  ;\n\t\t\t  \nALPHA=[A-Za-z]\nDIGITO=[0-9]\nALPHANUMERIC={ALPHA}|{DIGITO}\n\nNUMERO=({DIGITO})+ | ({DIGITO})+.({DIGITO})+\nIDENTIFICADOR={ALPHA}({ALPHANUMERIC} | [_])*\n\nTAB=\"T.\"({IDENTIFICADOR})\nSALTO=\\n|\\r|\\r\\n\nESPACIO=[ \\t\\f]\nCADENA = \\\" ({NUMERO} | {ALPHANUMERIC}| _|-|%)* \\\"");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
