/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import Utilidades.Filtro;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import traductor.AnalizadorSintactico;
import traductor.AnalizadorLexico;

/**
 *
 * @author Absa
 */
public class TraductorGUI extends javax.swing.JFrame implements ActionListener {

    JMenu menuAyuda;
    JMenuItem item1M1, item2M1, item1M2, item2M2, item3M2, item4M2, item5M2, item6M2, item7M2, item8M2, item9M2,itemAy1,itemAy2;
    String QUERYact;
    int posJTPA = 0;

    /**
     * Creates new form TraductorGUI
     */
    public TraductorGUI(String[] colores) {
        initComponents();
        setLocationRelativeTo(null);
        //  asd();
        if (!ConfConnection.flagCon) {
            setSize(720, 325);
            jCBtablas.setEnabled(false);
            jCBcols.setEnabled(false);
            jTres.setEnabled(false);
            jTres.setVisible(false);
        }
        menuAyuda = new JMenu("Ayuda");
        setResizable(false);
        DefaultTableModel tmodel = (DefaultTableModel) (jTres.getModel());
        tmodel.setColumnCount(0);
        //new JScrollPane(jTres, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //jTres.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jCBaccion.removeAllItems();
        jCBtablas.removeAllItems();
        jCBcols.removeAllItems();
        jCBaccion.addItem("Π"/*proyección*/);
        jCBaccion.addItem("σ" /*selección*/);
        jCBaccion.addItem("∪");
        jCBaccion.addItem("∩");
        jCBaccion.addItem("*");
        jCBaccion.addItem("-");
        jCBaccion.addItem("join");
        jCBaccion.setVisible(false);
        jLabel1.setVisible(false);

        if (ConfConnection.flagCon) {
            getNamesTables();
        }
        jmenubar();
        jTPalgebra.setText("");
        jTPalgebra.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                posJTPA = jTPalgebra.getCaretPosition();
            }
        });
    }

    public void jmenubar() {
        item1M1 = new JMenuItem("Abrir");
        item2M1 = new JMenuItem("Guardar");

        AbstractAction accProy = new AbstractAction("Π") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "Π( lc, tabla)", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                //jTPalgebra.setText(jTPalgebra.getText()+"Π( lc, tabla)");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 8);
            }
        };
        accProy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('P', Event.CTRL_MASK));

        AbstractAction accSelect = new AbstractAction("σ") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "σ( cond, tabla)", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                //jTPalgebra.setText(jTPalgebra.getText()+"σ( cond, tabla)");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 8);
            }
        };
        accSelect.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('S', Event.CTRL_MASK));

        AbstractAction accUnion = new AbstractAction("∪") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "∪", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        accUnion.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('U', Event.CTRL_MASK));

        AbstractAction accInter = new AbstractAction("∩") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "reg1 ∩ reg2( lc )", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"reg1 ∩ reg2( lc )");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 14);
            }
        };
        accInter.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('I', Event.CTRL_MASK));

        AbstractAction accCruz = new AbstractAction("*") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "*", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"*");
            }
        };
        accCruz.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('K', Event.CTRL_MASK));

        AbstractAction accDif = new AbstractAction("-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "reg1 - reg2 ( cond )", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"reg1 - reg2 ( cond )");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 17);
            }
        };
        accDif.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('D', Event.CTRL_MASK));

        AbstractAction accJoin = new AbstractAction("JOIN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "JOIN(reg1, reg2)", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"JOIN(reg1, reg2)");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 7);
            }
        };
        accJoin.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('J', Event.CTRL_MASK));
        
        itemAy1=new JMenuItem("Gramatica");
        itemAy2=new JMenuItem("Info");
        
        itemAy1.addActionListener(this);
        itemAy2.addActionListener(this);
        
        item1M2 = new JMenuItem(accProy);
        item2M2 = new JMenuItem(accSelect);
        item3M2 = new JMenuItem(accUnion);
        item4M2 = new JMenuItem(accInter);
        item5M2 = new JMenuItem(accCruz);
        item6M2 = new JMenuItem(accDif);
        item7M2 = new JMenuItem(accJoin);
        item8M2 = new JMenuItem("Copiar  Ctrl+c");
        item9M2 = new JMenuItem("Pegar   Ctrl+v");

        item1M1.addActionListener(this);
        item2M1.addActionListener(this);
        item1M2.addActionListener(this);
        item2M2.addActionListener(this);
        item3M2.addActionListener(this);
        item4M2.addActionListener(this);
        item5M2.addActionListener(this);
        item6M2.addActionListener(this);
        item7M2.addActionListener(this);
        item8M2.addActionListener(this);

        jMenu1.add(item1M1);
        jMenu1.add(new JSeparator());
        jMenu1.add(item2M1);

        jMenu2.add(item8M2);
        jMenu2.add(item9M2);
        jMenu2.add(new JSeparator());
        jMenu2.add(item1M2);
        jMenu2.add(item2M2);
        jMenu2.add(item3M2);
        jMenu2.add(item4M2);
        jMenu2.add(item5M2);
        jMenu2.add(item6M2);
        jMenu2.add(item7M2);
        
        menuAyuda.add(itemAy1);
        menuAyuda.add(itemAy2);

        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenuBar.add(menuAyuda);




    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jCBaccion = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jCBtablas = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jCBcols = new javax.swing.JComboBox();
        jBtrans = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTPalgebra = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTPsentencia = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTres = new javax.swing.JTable();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Acción:");

        jCBaccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBaccionActionPerformed(evt);
            }
        });

        jLabel2.setText("Tablas:");

        jCBtablas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBtablas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBtablasActionPerformed(evt);
            }
        });

        jLabel3.setText("Columna:");

        jCBcols.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBcols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBcolsActionPerformed(evt);
            }
        });

        jBtrans.setText("Transformar");
        jBtrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtransActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jTPalgebra);

        jLabel6.setText("Algebra Relacional:");

        jScrollPane5.setViewportView(jTPsentencia);

        jLabel7.setText("Sentencia SQL:");

        jTres.setAutoCreateRowSorter(true);
        jTres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTres);

        jMenu1.setText("File");
        jMenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar.add(jMenu2);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCBaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jBtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jCBtablas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jCBcols, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane4))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCBtablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jCBcols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCBtablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBtablasActionPerformed

        if (jCBtablas.getSelectedIndex() != -1) {
            String tabla = (String) jCBtablas.getSelectedItem();
            appendTexto(Color.RED, "T." + tabla);
            getColsName(tabla);
        }
        jTPalgebra.requestFocus();
//        jTPalgebra.setCaretPosition(posJTPA+jCBcols.getSelectedItem().toString().length());
    }//GEN-LAST:event_jCBtablasActionPerformed

    private void jCBcolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBcolsActionPerformed

        if (jCBcols.getSelectedIndex() != -1) {
            appendTexto(Color.GRAY, jCBcols.getSelectedItem().toString());
        }
        jTPalgebra.requestFocus();
        //      jTPalgebra.setCaretPosition(posJTPA+jCBcols.getSelectedItem().toString().length());
    }//GEN-LAST:event_jCBcolsActionPerformed

    private void jCBaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBaccionActionPerformed
        if (jCBaccion.getSelectedIndex() != -1) {
            appendTexto(Color.BLUE, (jCBaccion.getSelectedItem().toString().equals("join") ? jCBaccion.getSelectedItem().toString().toUpperCase() : jCBaccion.getSelectedItem().toString()));

        }
    }//GEN-LAST:event_jCBaccionActionPerformed

    private void jBtransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtransActionPerformed
        escribir("Programa.txt");
        AnalizadorLexico lex;
        try {
            lex = new AnalizadorLexico(new FileReader("Programa.txt"));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AnalizadorSintactico asin = new AnalizadorSintactico(lex);
        try {
            asin.parse();
            QUERYact = asin.getResultado();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
        }
        if (QUERYact.length() <= 0) {
            jTPsentencia.setText("");
            JOptionPane.showMessageDialog(this, "No se generaron consultas", "Error:", JOptionPane.ERROR_MESSAGE);
            return;
        }

        jTPsentencia.setText(QUERYact);
        if (ConfConnection.flagCon) {
            DefaultTableModel tmodel = (DefaultTableModel) (jTres.getModel());
            tmodel.setColumnCount(0);
            tmodel.setRowCount(0);
            int numCols = 0;
            try {
                Statement stmt = ConfConnection.conexion.createStatement();
                ResultSet rs = stmt.executeQuery(QUERYact);
                ResultSetMetaData rsm = rs.getMetaData();
                numCols = rsm.getColumnCount();
                for (int count = 1; count <= numCols; count++) {
                    tmodel.addColumn(rsm.getColumnName(count));
                }
                while (rs.next()) {
                    Object[] fila = new Object[numCols];
                    for (int i = 0; i < numCols; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    tmodel.addRow(fila);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        jTres.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }//GEN-LAST:event_jBtransActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TraductorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TraductorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TraductorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TraductorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TraductorGUI(args).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(TraductorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtrans;
    private javax.swing.JComboBox jCBaccion;
    private javax.swing.JComboBox jCBcols;
    private javax.swing.JComboBox jCBtablas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextPane jTPalgebra;
    private javax.swing.JTextPane jTPsentencia;
    private javax.swing.JTable jTres;
    // End of variables declaration//GEN-END:variables

    public void escribir(String direccion) {
        //metodo que guarda lo que esta escrito en un archivo de texto
        try {
            FileWriter writer = new FileWriter(direccion);
            PrintWriter print = new PrintWriter(writer);
            print.print(jTPalgebra.getText());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getNamesTables() {
        try {
            DatabaseMetaData md = ConfConnection.conexion.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                jCBtablas.addItem(rs.getString(3));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getColsName(String tabla) {
        try {
            jCBcols.removeAllItems();
            DatabaseMetaData md = ConfConnection.conexion.getMetaData();
            ResultSet rs = md.getColumns(null, null, tabla, null);
            jCBcols.addItem("");
            while (rs.next()) {
                jCBcols.addItem(rs.getString(4));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void appendTexto(Color color, String texto) {
        StyledDocument doc = jTPalgebra.getStyledDocument();
        Style syle = doc.addStyle("txt", null);
        StyleConstants.setForeground(syle, color);
        try {
            doc.insertString(this.posJTPA, texto, syle);
            jTPalgebra.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void asd() {
        String x = "hola como estas";
        System.out.println(x.substring(3));
        Stack<String> hola=new Stack();
        for (String string : hola) {
            System.out.println(string);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getSource());
        String arch;

        if (e.getSource() == item1M1) {
            try {
                arch = abrirArchivo();
                if (arch.isEmpty()) {
                    return;
                }
                jTPalgebra.setText(arch);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error:", JOptionPane.DEFAULT_OPTION);
            }
        } else if (e.getSource() == item2M1) {
            try {
                crearArchivo(jTPalgebra.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.DEFAULT_OPTION);
            }
        }else if (e.getSource() == itemAy1) {
            infoGramatica infg=new infoGramatica();
            infg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            infg.setVisible(true);
        }else if (e.getSource() == itemAy2) {
            infoPeriodo infg=new infoPeriodo();
            infg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            infg.setVisible(true);
        }
    }

    public String abrirArchivo() throws IOException {
        String s = "", cad = "";
        try {
            JFileChooser jf = new JFileChooser();
            jf.setFileFilter(new Filtro());
            jf.setDialogTitle("Abrir Archivos de Texto");
            int returnVal = jf.showOpenDialog(this);

            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return "";
            }
            File abrir = jf.getSelectedFile();
            //setNombreArchivo(abrir.getName());
            if (abrir.getAbsolutePath().endsWith(".txt")) {
                if (abrir != null) {
                    FileReader archivo = new FileReader(abrir);
                    BufferedReader leer = new BufferedReader(archivo);
                    while ((s = leer.readLine()) != null) {
                        cad += s + "\n";
                    }
                    leer.close();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Solo se permiten archivos con extension *.txt");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        return cad;
    }

    public void crearArchivo(String datos) throws IOException {
        try {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new Filtro());
            fc.setDialogTitle("Guardar archivos de texto");
            int open=fc.showSaveDialog(this);
            if(open!=JFileChooser.APPROVE_OPTION)
                return;
            fc.getFileFilter();

            String path = fc.getSelectedFile().getPath();
            
            File guardar = new File(path + ".txt");
            //setNombreArchivo(path+".txt");
            if (guardar != null) {
                FileWriter guardaTexto = new FileWriter(guardar);
                guardaTexto.write(datos);
                guardaTexto.close();
            }
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, ioe.getMessage());
        }
    }
}
