package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import domain.Mesa;
import domain.Pedido;
import domain.Restaurante;

// Ventana de cocina con tres listas: en cola, preparando y listo.
public class VentanaCocina extends JFrame {

	private static final long serialVersionUID = 1L;

	private Restaurante restaurante;
	private DefaultListModel<Pedido> modeloCola = new DefaultListModel<>();
	private DefaultListModel<Pedido> modeloPreparando = new DefaultListModel<>();
	private DefaultListModel<Pedido> modeloListo = new DefaultListModel<>();
	private JComboBox<Mesa> cbMesa;
	private JTextField txtPlatos;

	public VentanaCocina(Restaurante restaurante) {
		this.restaurante = restaurante;

		setTitle("Cocina");
		setSize(750, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// arriba: nuevo pedido
		JPanel pnlArriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cbMesa = new JComboBox<>();
		for (Mesa m : restaurante.getMesas()) {
			cbMesa.addItem(m);
		}
		txtPlatos = new JTextField(20);
		JButton btnAnadir = new JButton("Añadir pedido");
		pnlArriba.add(new JLabel("Mesa:"));
		pnlArriba.add(cbMesa);
		pnlArriba.add(new JLabel("Platos:"));
		pnlArriba.add(txtPlatos);
		pnlArriba.add(btnAnadir);
		add(pnlArriba, BorderLayout.NORTH);

		// centro: las tres listas
		JPanel pnlListas = new JPanel(new GridLayout(1, 3, 5, 5));
		pnlListas.add(crearLista("En cola", modeloCola));
		pnlListas.add(crearLista("Preparando", modeloPreparando));
		pnlListas.add(crearLista("Listo", modeloListo));
		add(pnlListas, BorderLayout.CENTER);

		add(new JLabel(" Doble clic en un pedido para pasarlo al siguiente estado"), BorderLayout.SOUTH);

		btnAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				anadirPedido();
			}
		});

		cargarListas();
	}

	private JScrollPane crearLista(String titulo, DefaultListModel<Pedido> modeloLista) {
		JList<Pedido> lista = new JList<>(modeloLista);
		lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && lista.getSelectedValue() != null) {
					avanzar(lista.getSelectedValue());
				}
			}
		});
		JScrollPane scroll = new JScrollPane(lista);
		scroll.setBorder(BorderFactory.createTitledBorder(titulo));
		return scroll;
	}

	private void cargarListas() {
		modeloCola.clear();
		modeloPreparando.clear();
		modeloListo.clear();
		for (Pedido p : restaurante.getPedidos()) {
			if (p.getEstado().equals("EN COLA")) {
				modeloCola.addElement(p);
			} else if (p.getEstado().equals("PREPARANDO")) {
				modeloPreparando.addElement(p);
			} else {
				modeloListo.addElement(p);
			}
		}
	}

	private void anadirPedido() {
		String platos = txtPlatos.getText().trim();
		if (platos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Escribe los platos del pedido", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		restaurante.getPedidos().add(new Pedido((Mesa) cbMesa.getSelectedItem(), platos));
		txtPlatos.setText("");
		cargarListas();
	}

	private void avanzar(Pedido p) {
		if (p.getEstado().equals("EN COLA")) {
			p.setEstado("PREPARANDO");
		} else if (p.getEstado().equals("PREPARANDO")) {
			p.setEstado("LISTO");
		} else {
			int resp = JOptionPane.showConfirmDialog(this, "¿Pedido servido? Se quitara de la lista", "Servido",
					JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				restaurante.getPedidos().remove(p);
			}
		}
		cargarListas();
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
