package com.example.miniinventario2;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.example.util.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Inventario {

	private JFrame frame;
	private JTextField txtCodigoBarras;
	private JTextField txtProducto;
	private JTextField txtPrecio;
	private JTextField txtExistencias;
	private JTextField txtId;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnEditar, btnEliminar, btnLimpiar, btnToInventario, btnGenerarReporte;
	private JPanel panel, panel_1, panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventario window = new Inventario();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inventario() {
		initialize();
		cargarTabla();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Mini Inventario 2"); // Asigna titulo a la ventana
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Asigna un icono personalizado a la ventana
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/dairy-products.png"));
		frame.setIconImage(image);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 277, 239);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 257, 218);
		panel.add(scrollPane);
		
		// Modelo de la tabla
		tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Código de barras");
		tableModel.addColumn("Producto");
		tableModel.addColumn("Precio");
		tableModel.addColumn("Existencias");
		
		table = new JTable();
		// Evento para seleccionar datos al hacer clic en la tabla
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = table.getSelectedRow();
				
				if (filaSeleccionada >= 0) {
					txtId.setText(tableModel.getValueAt(filaSeleccionada, 0).toString());
					txtCodigoBarras.setText(tableModel.getValueAt(filaSeleccionada, 1).toString());
					txtProducto.setText(tableModel.getValueAt(filaSeleccionada, 2).toString());
					txtPrecio.setText(tableModel.getValueAt(filaSeleccionada, 3).toString());
					txtExistencias.setText(tableModel.getValueAt(filaSeleccionada, 4).toString());
					
					btnEditar.setEnabled(true);
					btnEliminar.setEnabled(true);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		panel_1 = new JPanel();
		panel_1.setBounds(297, 11, 277, 239);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtCodigoBarras = new JTextField();
		txtCodigoBarras.setBounds(142, 11, 125, 20);
		panel_1.add(txtCodigoBarras);
		txtCodigoBarras.setColumns(10);
		
		txtProducto = new JTextField();
		txtProducto.setColumns(10);
		txtProducto.setBounds(142, 42, 125, 20);
		panel_1.add(txtProducto);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(142, 73, 125, 20);
		panel_1.add(txtPrecio);
		
		txtExistencias = new JTextField();
		txtExistencias.setColumns(10);
		txtExistencias.setBounds(142, 104, 125, 20);
		panel_1.add(txtExistencias);
		
		txtId = new JTextField();
		txtId.setVisible(false);
		txtId.setColumns(10);
		txtId.setBounds(10, 135, 125, 20);
		panel_1.add(txtId);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarProducto();
				limpiar();
			}
		});
		btnGuardar.setBounds(10, 172, 125, 23);
		panel_1.add(btnGuardar);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarProducto();
			}
		});
		btnEditar.setBounds(142, 172, 125, 23);
		btnEditar.setEnabled(false); // Desactivado hasta que se seleccione un usuario
		panel_1.add(btnEditar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarProducto();
			}
		});
		btnEliminar.setBounds(10, 205, 125, 23);
		btnEliminar.setEnabled(false); // Desactivado hasta que se seleccione un usuario
		panel_1.add(btnEliminar);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setBounds(142, 206, 125, 23);
		panel_1.add(btnLimpiar);
		
		JLabel lblCodigoBarras = new JLabel("Código de barras:");
		lblCodigoBarras.setBounds(10, 14, 125, 14);
		panel_1.add(lblCodigoBarras);
		
		JLabel lblProducto = new JLabel("Producto:");
		lblProducto.setBounds(10, 45, 125, 14);
		panel_1.add(lblProducto);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 76, 125, 14);
		panel_1.add(lblPrecio);
		
		JLabel lblExistencias = new JLabel("Existencias:");
		lblExistencias.setBounds(10, 107, 125, 14);
		panel_1.add(lblExistencias);
		
		JButton btnToReportes = new JButton("Reportes");
		btnToReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchReportes(panel_2);
			}
		});
		btnToReportes.setBounds(142, 135, 125, 23);
		panel_1.add(btnToReportes);
		
		panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 564, 239);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		btnGenerarReporte = new JButton("Generar Reporte");
		btnGenerarReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarReporte();
			}
		});
		btnGenerarReporte.setBounds(210, 100, 140, 23);
		panel_2.add(btnGenerarReporte);
		
		btnToInventario = new JButton("Inventario");
		btnToInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchReportes(panel);
			}
		});
		btnToInventario.setBounds(0, 0, 125, 23);
		panel_2.add(btnToInventario);
		
		panel.setVisible(true);
		panel_1.setVisible(true);
		panel_2.setVisible(false);
	}
	
	private void generarReporte() {
		try {
            // Obtiene la conexión de la base de datos MariaDB
            Connection conn = MariaDBConnection.getConnection();

            // Cargar el archivo del reporte .jasper
            InputStream rutaReporte = getClass().getResourceAsStream("/reports/reporte.jasper");
            if (rutaReporte == null) {
                throw new FileNotFoundException("No se encontró el archivo del reporte dentro del JAR");
            }
            
            // Obtener la ruta del logotipo
            InputStream rutaLogo = getClass().getResourceAsStream("/resources/report.png");
            if (rutaLogo == null) {
                throw new FileNotFoundException("No se encontró el logo dentro del JAR");
            }
            
            // Pasar la ruta como parámetro al reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("LOGO_PATH", rutaLogo);

            // Llenar el reporte con datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, parametros, conn);

            // Mostrar el reporte en una ventana JasperViewer
            JasperViewer.viewReport(jasperPrint, false);
            
            // Para exportar a PDF (se puede exportar desde el visualizador de jasper)
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "reporte_productos.pdf");

            // Cerrar la conexión
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	private void switchReportes(JPanel currentPanel) {
		panel.setVisible(currentPanel == panel);
		panel_1.setVisible(currentPanel == panel);
		panel_2.setVisible(currentPanel == panel_2);
	}
	
	private void guardarProducto() {
		String codigoBarras = txtCodigoBarras.getText();
		String producto = txtProducto.getText();
		String precioText = txtPrecio.getText();
		String existenciasText = txtExistencias.getText();
		double precio;
		int existencias;
		
		// Comprueba que se hallan insertado datos en los campos de texto
		if (codigoBarras.isEmpty() || producto.isEmpty() || precioText.isEmpty() || existenciasText.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
        	precio = Double.parseDouble(txtPrecio.getText());
    		existencias = Integer.parseInt(txtExistencias.getText());
        }
		
		try {
			// Trae la conexion de la clase Conexion
			Connection conn = MariaDBConnection.getConnection();
			String sql = "INSERT INTO productos (codigo_barras, nombre, precio, existencias) VALUES (?, ?, ?, ?)";
			// Prepara la consulta
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, codigoBarras);
			statement.setString(2, producto);
			statement.setDouble(3, precio);
			statement.setInt(4, existencias);
			// Ejecuta la consulta
			int filasInsertadas = statement.executeUpdate();
			
			if (filasInsertadas > 0) {
				JOptionPane.showMessageDialog(null, "Producto almacenado correctamente!");
			} else {
				JOptionPane.showMessageDialog(null, "Ocurrió un problema al guardar...");
			}
			
			cargarTabla();
			
			// Cierra las conexiones
			statement.close();
			conn.close();
		} catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actualizarProducto() {
		int productoId = Integer.parseInt(txtId.getText());
		String codigoBarras = txtCodigoBarras.getText();
		String producto = txtProducto.getText();
		double precio = Double.parseDouble(txtPrecio.getText());
		int existencias = Integer.parseInt(txtExistencias.getText());
		
		try {
			// Trae la conexion de la clase Conexion
			Connection conn = MariaDBConnection.getConnection();
			String sql = "UPDATE productos SET codigo_barras = ?, nombre = ?, precio = ?, existencias = ? WHERE id = ?";
			// Prepara la consulta
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, codigoBarras);
			statement.setString(2, producto);
			statement.setDouble(3, precio);
			statement.setInt(4, existencias);
			statement.setInt(5, productoId);
			// Ejecuta la consulta
			int filasInsertadas = statement.executeUpdate();
			
			if (filasInsertadas > 0) {
				JOptionPane.showMessageDialog(null, "Producto actualizado correctamente!");
				cargarTabla();
				limpiar();
				btnEditar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Ocurrió un problema al actualizar la información...");
			}
			
			// Cierra las conexiones
			statement.close();
			conn.close();
		} catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void eliminarProducto() {
		int productoId = Integer.parseInt(txtId.getText());
		
		try {
			// Trae la conexion de la clase Conexion
			Connection conn = MariaDBConnection.getConnection();
			String sql = "DELETE FROM productos WHERE id = ?";
			// Prepara la consulta
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, productoId);
			// Ejecuta la consulta
			int filasInsertadas = statement.executeUpdate();
			
			if (filasInsertadas > 0) {
				JOptionPane.showMessageDialog(null, "Producto eliminado correctamente!");
				cargarTabla();
				limpiar();
				btnEditar.setEnabled(false);
				btnEliminar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Ocurrió un problema al actualizar la información...");
			}
			
			// Cierra las conexiones
			statement.close();
			conn.close();
		} catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cargarTabla() {
		tableModel.setRowCount(0); // Permite actualizar las filas y no volverlas a colocar si se llama al metodo más de una vez
		table.setModel(tableModel);
		
		try {
			// Trae la conexion de la clase Conexion
			Connection conn = MariaDBConnection.getConnection();
			String sql = "SELECT * FROM productos";
			// Prepara la consulta
			PreparedStatement statement = conn.prepareStatement(sql);
			
			// Ejecuta la consulta
			ResultSet resultado = statement.executeQuery();
			
			while (resultado.next()) {
				int id= resultado.getInt("id");
				String codigoBarras = resultado.getString("codigo_barras");
				String producto = resultado.getString("nombre");
				double precio = resultado.getDouble("precio");
				int existencias = resultado.getInt("existencias");
				
				tableModel.addRow(new Object[] {id, codigoBarras, producto, precio, existencias});
			}
			resultado.close();
			statement.close();
			conn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limpiar() {
		txtId.setText("");
		txtCodigoBarras.setText("");
		txtProducto.setText("");
		txtPrecio.setText("");
		txtExistencias.setText("");
		btnEditar.setEnabled(false);
		btnEliminar.setEnabled(false);
	}
}
