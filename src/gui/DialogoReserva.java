package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import domain.Cliente;
import domain.Mesa;
import domain.Reserva;
import domain.Restaurante;

// Dialogo para crear una reserva nueva.
public class DialogoReserva extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private Restaurante restaurante;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtFecha;
	private JComboBox<String> cbHora;
	private JSpinner spPersonas;
	private JComboBox<Mesa> cbMesa;

	public DialogoReserva(JFrame padre, Restaurante restaurante) {
		super(padre, "Nueva reserva", true);
		this.restaurante = restaurante;

		txtNombre = new JTextField();
		txtTelefono = new JTextField();
		txtFecha = new JTextField(LocalDate.now().format(FORMATO));
		cbHora = new JComboBox<>(new String[] { "13:00", "13:30", "14:00", "14:30", "20:30", "21:00", "21:30", "22:00" });
		spPersonas = new JSpinner(new SpinnerNumberModel(2, 1, 12, 1));
		cbMesa = new JComboBox<>();
		for (Mesa m : restaurante.getMesas()) {
			cbMesa.addItem(m);
		}

		JPanel pnlDatos = new JPanel(new GridLayout(6, 2, 5, 5));
		pnlDatos.add(new JLabel("Nombre:"));
		pnlDatos.add(txtNombre);
		pnlDatos.add(new JLabel("Telefono:"));
		pnlDatos.add(txtTelefono);
		pnlDatos.add(new JLabel("Dia (dd/mm/aaaa):"));
		pnlDatos.add(txtFecha);
		pnlDatos.add(new JLabel("Hora:"));
		pnlDatos.add(cbHora);
		pnlDatos.add(new JLabel("Personas:"));
		pnlDatos.add(spPersonas);
		pnlDatos.add(new JLabel("Mesa:"));
		pnlDatos.add(cbMesa);

		JButton btnGuardar = new JButton("Guardar");
		JButton btnCancelar = new JButton("Cancelar");
		JPanel pnlBotones = new JPanel();
		pnlBotones.add(btnGuardar);
		pnlBotones.add(btnCancelar);

		add(pnlDatos, BorderLayout.CENTER);
		add(pnlBotones, BorderLayout.SOUTH);

		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setSize(350, 280);
		setLocationRelativeTo(padre);
	}

	private void guardar() {
		String nombre = txtNombre.getText().trim();
		String telefono = txtTelefono.getText().trim();
		String hora = (String) cbHora.getSelectedItem();
		int personas = (Integer) spPersonas.getValue();
		Mesa mesa = (Mesa) cbMesa.getSelectedItem();

		if (nombre.isEmpty() || telefono.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Rellena el nombre y el telefono", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (telefono.length() != 9) {
			JOptionPane.showMessageDialog(this, "El telefono tiene que tener 9 numeros", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		LocalDate fecha;
		try {
			fecha = LocalDate.parse(txtFecha.getText().trim(), FORMATO);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(this, "La fecha no es correcta. Usa el formato dd/mm/aaaa", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (personas > mesa.getCapacidad()) {
			JOptionPane.showMessageDialog(this, "En la mesa " + mesa.getNumero() + " solo caben "
					+ mesa.getCapacidad() + " personas", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (restaurante.estaReservada(mesa, fecha, hora)) {
			JOptionPane.showMessageDialog(this, "La mesa ya esta reservada ese dia a esa hora", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// si el cliente no existe lo creamos
		Cliente cliente = restaurante.buscarCliente(telefono);
		if (cliente == null) {
			cliente = new Cliente(nombre, telefono);
			restaurante.getClientes().add(cliente);
		}

		restaurante.getReservas().add(new Reserva(cliente, fecha, hora, personas, mesa));
		JOptionPane.showMessageDialog(this, "Reserva guardada");
		dispose();
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
