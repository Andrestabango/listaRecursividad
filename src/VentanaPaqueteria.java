import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPaqueteria {
    private JPanel Ventana;
    private JTabbedPane tabbedPane1;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton agregarButton;
    private JTextField textField2;
    private JButton totalPaqueteaButton;
    private JButton totalPesoButton;
    private JComboBox comboBox3;
    private JButton totalPesoPorCiudadButton;
    private JList list1;
    private JButton modificarButton;
    private JTextField modificartextField3;
    private JButton modificarEstadoButton;
    private JButton ordenarPorBurbujaButton;
    private JList list2;
    private JList list3;
    private JButton ordenarPorInsercionButton;
    private JButton calcularPaquetesSegunSuButton;

    private Lista paquetes = new Lista();

    public VentanaPaqueteria(){


        quemarDatos();
        llenarJlist();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paquetes.adicionarElementos(new Paqueteria(
                            Integer.parseInt(spinner1.getValue().toString()),
                            Double.parseDouble(textField2.getText()),
                            comboBox1.getSelectedItem().toString(),
                            comboBox2.getSelectedItem().toString(),
                            textField1.getText()
                    ));
                    JOptionPane.showMessageDialog(null, "Paquete agregado:");
                    System.out.println(paquetes.listarPaquetes());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                setearDatos();
                llenarJlist();
            }
        });

        totalPaqueteaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "El total de paquetes es: "+ paquetes.sumarTotalPaquetes());
            }
        });
        totalPesoPorCiudadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "El peso total de los paquetes es: "+paquetes.sumarTotalPesoCiudad(comboBox3.getSelectedItem().toString()));
            }
        });
        totalPesoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"El total de peso de la ciudad seleccionada es : "+paquetes.sumarTotalPeso());
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list1.getSelectedIndex()!=-1){
                    int indice= list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getServiEntrega().get(indice);
                    spinner1.setValue(pa.getTracking());
                    textField2.setText(String.valueOf(pa.getPeso()));
                    comboBox1.setSelectedItem(pa.getCiudadEntrega());
                    comboBox2.setSelectedItem(pa.getCiudadRecepcion());
                    textField1.setText(pa.getCedulaReceptor());

                }

            }
        });
        modificarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackingText = modificartextField3.getText();
                if (!trackingText.isEmpty()) {
                    try {
                        int trackingNumber = Integer.parseInt(trackingText);
                        boolean paqueteEncontrado = false;
                        for (Paqueteria paquete : paquetes.getServiEntrega()) {
                            if (paquete.getTracking() == trackingNumber) {
                                String estadoActual = paquete.getEstado();
                                if (estadoActual.equals("Receptado")) {
                                    paquete.setEstado("Enviado");
                                    JOptionPane.showMessageDialog(null, "El estado del paquete ha sido modificado a 'Enviado'.");
                                } else if (estadoActual.equals("Enviado")) {
                                    paquete.setEstado("Entregado");
                                    JOptionPane.showMessageDialog(null, "El estado del paquete ha sido modificado a 'Entregado'.");
                                } else if (estadoActual.equals("Entregado")) {
                                    JOptionPane.showMessageDialog(null, "El paquete ya ha sido entregado.");
                                }
                                paqueteEncontrado = true;
                                list1.repaint();
                                break;
                            }
                        }

                        if (!paqueteEncontrado) {
                            JOptionPane.showMessageDialog(null, "Paquete no encontrado.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un número de tracking válido.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese el número de tracking.");
                }
            }
        });
        ordenarPorBurbujaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    llenarJlistInicial();
                    llenarJlistTracking();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        });
        ordenarPorInsercionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    llenarJlistInicial();
                    llenarJlistPeso();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    Paqueteria paqueteSeleccionado = (Paqueteria) list1.getSelectedValue();
                    int tracking = paqueteSeleccionado.getTracking();

                    double nuevoPeso = Double.parseDouble(textField2.getText());
                    String nuevaCiudadEntrega = comboBox1.getSelectedItem().toString();
                    String nuevaCiudadRecepcion = comboBox2.getSelectedItem().toString();
                    String nuevaCedulaReceptor = textField1.getText();

                    try {
                        paquetes.editarEmpleado(tracking, nuevoPeso, nuevaCiudadEntrega, nuevaCiudadRecepcion, nuevaCedulaReceptor);
                        llenarJlist();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        calcularPaquetesSegunSuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int totalReceptados = paquetes.calcularTotalPaquetesEstado("Receptado");
                    int totalEnviados = paquetes.calcularTotalPaquetesEstado("Enviado");
                    int totalEntregados = paquetes.calcularTotalPaquetesEstado("Entregado");

                    String mensaje = "Total de paquetes receptados: " + totalReceptados + "\n" +
                            "Total de paquetes enviados: " + totalEnviados + "\n" +
                            "Total de paquetes entregados: " + totalEntregados;

                    JOptionPane.showMessageDialog(null, mensaje);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al calcular el total de paquetes: " + ex.getMessage());
                }
            }
        });
    }
    public void setearDatos(){
        spinner1.setValue(0);
        textField2.setText("");
        comboBox1.setSelectedIndex(0);
        comboBox2.setSelectedIndex(0);
        textField1.setText("");
    }

    public void quemarDatos(){
        try {
            paquetes.adicionarElementos(new Paqueteria(1,45,"Quito","Guayaquil","1753468311"));
            paquetes.adicionarElementos(new Paqueteria(2,89,"Quito","Cuenca","1753468312"));
            paquetes.adicionarElementos(new Paqueteria(3,69,"Guayaquil","Riobamba","1753468313"));
            paquetes.adicionarElementos(new Paqueteria(10,69,"Guayaquil","Riobamba","175346833"));
            paquetes.adicionarElementos(new Paqueteria(7,14,"Guayaquil","Riobamba","175348313"));

        } catch (Exception e) {
        }

    }

    public void llenarJlist(){
        DefaultListModel<Paqueteria> dlm = new DefaultListModel<>();
        dlm.removeAllElements();
        for (Paqueteria p : paquetes.getServiEntrega()) {
            dlm.addElement(p);
        }
        list1.setModel(dlm);
    }

    public void llenarJlistInicial(){
        DefaultListModel dlm = new DefaultListModel<>();
        dlm.removeAllElements();
        for(Paqueteria p:paquetes.getServiEntrega()){
            dlm.addElement(p);
        }
        list2.setModel(dlm);
    }

    public void llenarJlistTracking() throws Exception {
        DefaultListModel dlm1 = new DefaultListModel<>();
        List<Paqueteria> listaT= paquetes.ordenarBurbuja();
        for(Paqueteria p:listaT){
            dlm1.addElement(p);
        }
        list3.setModel(dlm1);
    }

    public void llenarJlistPeso() throws Exception {
        DefaultListModel dlm2 = new DefaultListModel<>();
        List<Paqueteria> listaP= paquetes.ordenarInsercion();
        for(Paqueteria p:listaP){
            dlm2.addElement(p);
        }
        list3.setModel(dlm2);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPaqueteria");
        frame.setContentPane(new VentanaPaqueteria().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
