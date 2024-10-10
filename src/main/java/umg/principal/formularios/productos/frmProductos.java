package umg.principal.formularios.productos;
//Mistborn Cambio mi vida
import umg.principal.DaseDatos.Service.ProductoService;
import umg.principal.DaseDatos.model.Producto;
import umg.principal.reportes.pruebas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmProductos {
    private JLabel lblTitulo;
    private JTextField textFieldProducto;
    private JLabel lblCodigo;
    private JTextField textFieldDescripcion;
    private JPanel lblDescripcion;
    private JLabel lblOrigen;
    private JButton buttonLimpiar;
    private JButton buttonGrabar;
    private JButton buttonBuscar;
    private JComboBox comboBoxOrigen;
    private JButton buttonSalir;
    private JComboBox comboBoxReportes;
    private JButton buttonReporte;

    public frmProductos() {

        //cargar valores del combobox origen con clave y valor de origen
        //ejemplo 1 china, 2 japon, 3 corea
        comboBoxOrigen.addItem("China");
        comboBoxOrigen.addItem("Japon");
        comboBoxOrigen.addItem("Corea");

        comboBoxReportes.addItem("Existencias menores a 20 unidades");
        comboBoxReportes.addItem("Reporte de Corea");
        comboBoxReportes.addItem("Precios Mayores a 2000");
        comboBoxReportes.addItem("Agrupado por país y ordenado de mayor a menor");


        buttonLimpiar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldProducto.setText("");
                textFieldDescripcion.setText("");
                comboBoxOrigen.setSelectedIndex(0);
            }
        });
        buttonGrabar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Producto: " + textFieldProducto.getText() + "\n" +
                        "Descripcion: " + textFieldDescripcion.getText() + "\n" +
                        "Origen: " + comboBoxOrigen.getSelectedItem().toString());


                Producto producto = new Producto();
                producto.setDescripcion(textFieldDescripcion.getText());
                producto.setOrigen(comboBoxOrigen.getSelectedItem().toString());
                //producto.setIdProducto(Integer.parseInt(textFieldProducto.getText()));
                try{
                    new ProductoService().crearProducto(producto);
                    JOptionPane.showMessageDialog(null, "Producto guardado correctamente");
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al guardar el producto");
                }



            }
        });
        buttonBuscar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {


                int idProducto = textFieldProducto.getText().isEmpty() ? 0 : Integer.parseInt(textFieldProducto.getText());
                try{
                    Producto productoEncontrado = new ProductoService().obtenerProducto(idProducto);
                    if(productoEncontrado != null){
                        JOptionPane.showMessageDialog(null, "Producto encontrado: " + productoEncontrado.getDescripcion());
                        //llenar los campos con los valores encontrados
                        textFieldDescripcion.setText(productoEncontrado.getDescripcion());
                        comboBoxOrigen.setSelectedItem(productoEncontrado.getOrigen());

                        //convertir idProducto a string


                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado");
                    }
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto");
                }
            }
        });
        buttonSalir.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxReportes.getSelectedItem().toString().equals("Existencias menores a 20 unidades")){
                    pruebas.GenerarReporteMenor20("cantidad <20");
                }if(comboBoxReportes.getSelectedItem().toString().equals("Reporte de Corea")){
                    pruebas.GenerarReporteMenor20("origen =  'Corea'");
                }if(comboBoxReportes.getSelectedItem().toString().equals("Precios Mayores a 2000")){
                    pruebas.GenerarReporteMenor20("precio >2000");
                }if(comboBoxReportes.getSelectedItem().toString().equals("Agrupado por país y ordenado de mayor a menor")){
                    pruebas.GenerarReportePorOrden("precio DESC");
                }else {
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmProductos");
        frame.setContentPane(new frmProductos().lblDescripcion);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //centrar el formulario
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
