package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.Cliente;
import domain.Restaurante;

// Ventana para ver, buscar, añadir y borrar clientes.
public class VentanaClientes extends JFrame {

	private static final long serialVersionUID = 1L;

	private Restaurante restaurante;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JTextField txtBuscar;
	private List<Cliente> mostrados = new ArrayList<>();

	public VentanaClientes(Restaurante restaurante) {
		this.restaurante = restaurante;

		setTitle("Clientes");
		setSize(500, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel pnlArriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtBuscar = new JTextField(15);
		JButton btnAnadir = new JButton("Añadir");
		JButton btnBorrar = new JButton("Borrar");
		pnlArriba.add(new JLabel("Buscar:"));
		pnlArriba.add(txtBuscar);
		pnlArriba.add(btnAnadir);
		pnlArriba.add(btnBorrar);
		add(pnlArriba, BorderLayout.NORTH);

		modelo = new DefaultTableModel(new String[] { "Nombre", "Telefono" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		// se va filtrando segun se escribe
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cargarTabla();
			}
		});
		btnAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				anadirCliente();
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarCliente();
			}
		});

		cargarTabla();
	}

	private void cargarTabla() {
		String texto = txtBuscar.getText().toLowerCase();
		modelo.setRowCount(0);
		mostrados.clear();
		for (Cliente c : restaurante.getClientes()) {
			if (c.getNombre().toLowerCase().contains(texto) || c.getTelefono().contains(texto)) {
				modelo.addRow(new Object[] { c.getNombre(), c.getTelefono() });
				mostrados.add(c);
			}
		}
	}

	private void anadirCliente() {
		String nombre = JOptionPane.showInputDialog(this, "Nombre del cliente:");
		if (nombre == null || nombre.trim().isEmpty()) {
			return;
		}
		String telefono = JOptionPane.showInputDialog(this, "Telefono:");
		if (telefono == null || telefono.trim().length() != 9) {
			JOptionPane.showMessageDialog(this, "El telefono tiene que tener 9 numeros", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		restaurante.getClientes().add(new Cliente(nombre.trim(), telefono.trim()));
		cargarTabla();
	}

	private void borrarCliente() {
		int fila = tabla.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Selecciona un cliente");
			return;
		}
		Cliente c = mostrados.get(fila);
		int resp = JOptionPane.showConfirmDialog(this, "¿Borrar a " + c.getNombre() + "?", "Borrar",
				JOptionPane.YES_NO_OPTION);
		if (resp == JOptionPane.YES_OPTION) {
			restaurante.getClientes().remove(c);
			cargarTabla();
		}
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
