package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.EstadoReserva;
import domain.Mesa;
import domain.Reserva;
import domain.Restaurante;

// Ventana principal: mesas a la izquierda y reservas de hoy a la derecha.
public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private Restaurante restaurante;
	private PanelMesas panelMesas;
	private JTable tabla;
	private DefaultTableModel modelo;
	private List<Reserva> reservasHoy;
	private JLabel lblInfo;

	public VentanaPrincipal(Restaurante restaurante) {
		this.restaurante = restaurante;

		setTitle("MesaYa - Reservas de hoy");
		setSize(950, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// botones de arriba
		JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnNueva = new JButton("Nueva reserva");
		JButton btnBorrar = new JButton("Borrar");
		JButton btnSentar = new JButton("Sentar clientes");
		JButton btnClientes = new JButton("Clientes");
		JButton btnCocina = new JButton("Cocina");
		pnlBotones.add(btnNueva);
		pnlBotones.add(btnBorrar);
		pnlBotones.add(btnSentar);
		pnlBotones.add(btnClientes);
		pnlBotones.add(btnCocina);
		add(pnlBotones, BorderLayout.NORTH);

		// mesas
		panelMesas = new PanelMesas(restaurante, this);
		add(panelMesas, BorderLayout.WEST);

		// tabla de reservas
		modelo = new DefaultTableModel(new String[] { "Hora", "Cliente", "Personas", "Mesa", "Estado" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		tabla.setRowHeight(25);
		tabla.setDefaultRenderer(Object.class, new ReservaRenderer());
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		lblInfo = new JLabel();
		add(lblInfo, BorderLayout.SOUTH);

		// eventos
		btnNueva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogoReserva d = new DialogoReserva(VentanaPrincipal.this, restaurante);
				d.setVisible(true);
				actualizar();
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarReserva();
			}
		});
		btnSentar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sentarClientes();
			}
		});
		btnClientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaClientes(restaurante).setVisible(true);
			}
		});
		btnCocina.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCocina(restaurante).setVisible(true);
			}
		});

		// tecla Supr para borrar
		tabla.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					borrarReserva();
				}
			}
		});

		actualizar();
	}

	public void actualizar() {
		reservasHoy = restaurante.getReservasDelDia(LocalDate.now());
		modelo.setRowCount(0);
		for (Reserva r : reservasHoy) {
			modelo.addRow(new Object[] { r.getHora(), r.getCliente().getNombre(), r.getPersonas(),
					r.getMesa().getNumero(), r.getEstado() });
		}
		lblInfo.setText(" Reservas de hoy: " + reservasHoy.size());
		panelMesas.repaint();
	}

	private Reserva getSeleccionada() {
		int fila = tabla.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Selecciona una reserva de la tabla");
			return null;
		}
		return reservasHoy.get(fila);
	}

	private void borrarReserva() {
		Reserva r = getSeleccionada();
		if (r != null) {
			int resp = JOptionPane.showConfirmDialog(this, "¿Borrar la reserva de " + r.getCliente().getNombre() + "?",
					"Borrar", JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				restaurante.getReservas().remove(r);
				actualizar();
			}
		}
	}

	private void sentarClientes() {
		Reserva r = getSeleccionada();
		if (r != null) {
			r.setEstado(EstadoReserva.SENTADA);
			actualizar();
		}
	}

	// Se llama desde PanelMesas al hacer clic en una mesa.
	public void mesaPulsada(Mesa m) {
		Reserva r = restaurante.getReservaDeMesa(m, LocalDate.now());
		if (r == null) {
			JOptionPane.showMessageDialog(this, "La mesa " + m.getNumero() + " esta libre hoy");
		} else {
			JOptionPane.showMessageDialog(this, "Mesa " + m.getNumero() + "\nCliente: " + r.getCliente().getNombre()
					+ "\nHora: " + r.getHora() + "\nPersonas: " + r.getPersonas() + "\nEstado: " + r.getEstado());
		}
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
