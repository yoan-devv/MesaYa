package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Guarda los datos en listas. En la fase 2 esto se cambiara por la base de datos.
public class Restaurante {

	private List<Mesa> mesas = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>();
	private List<Pedido> pedidos = new ArrayList<>();

	public Restaurante() {
		cargarDatosPrueba();
	}

	public List<Mesa> getMesas() {
		return mesas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public List<Reserva> getReservasDelDia(LocalDate fecha) {
		List<Reserva> resultado = new ArrayList<>();
		for (Reserva r : reservas) {
			if (r.getFecha().equals(fecha)) {
				resultado.add(r);
			}
		}
		return resultado;
	}

	public Reserva getReservaDeMesa(Mesa mesa, LocalDate fecha) {
		for (Reserva r : getReservasDelDia(fecha)) {
			if (r.getMesa() == mesa && r.getEstado() != EstadoReserva.NO_PRESENTADA) {
				return r;
			}
		}
		return null;
	}

	// Devuelve "LIBRE", "RESERVADA" u "OCUPADA".
	public String getEstadoMesa(Mesa mesa, LocalDate fecha) {
		Reserva r = getReservaDeMesa(mesa, fecha);
		if (r == null) {
			return "LIBRE";
		} else if (r.getEstado() == EstadoReserva.SENTADA) {
			return "OCUPADA";
		} else {
			return "RESERVADA";
		}
	}

	// Mira si la mesa ya tiene una reserva ese dia a esa hora.
	public boolean estaReservada(Mesa mesa, LocalDate fecha, String hora) {
		for (Reserva r : getReservasDelDia(fecha)) {
			if (r.getMesa() == mesa && r.getHora().equals(hora)) {
				return true;
			}
		}
		return false;
	}

	public Cliente buscarCliente(String telefono) {
		for (Cliente c : clientes) {
			if (c.getTelefono().equals(telefono)) {
				return c;
			}
		}
		return null;
	}

	private void cargarDatosPrueba() {
		// 3 filas de 3 mesas
		int[] capacidades = { 2, 4, 4, 4, 6, 4, 2, 4, 8 };
		for (int i = 0; i < 9; i++) {
			int x = 30 + (i % 3) * 110;
			int y = 30 + (i / 3) * 90;
			mesas.add(new Mesa(i + 1, capacidades[i], x, y));
		}

		Cliente ane = new Cliente("Ane Etxeberria", "611204118");
		Cliente mikel = new Cliente("Mikel Arana", "655771900");
		Cliente laura = new Cliente("Laura Sanchez", "688123456");
		Cliente jon = new Cliente("Jon Zubizarreta", "699310482");
		clientes.add(ane);
		clientes.add(mikel);
		clientes.add(laura);
		clientes.add(jon);

		LocalDate hoy = LocalDate.now();
		Reserva r1 = new Reserva(ane, hoy, "13:30", 2, mesas.get(0));
		r1.setEstado(EstadoReserva.SENTADA);
		reservas.add(r1);
		reservas.add(new Reserva(mikel, hoy, "14:00", 4, mesas.get(3)));
		reservas.add(new Reserva(laura, hoy, "21:00", 7, mesas.get(8)));
		reservas.add(new Reserva(jon, hoy, "21:30", 3, mesas.get(1)));

		pedidos.add(new Pedido(mesas.get(0), "Ensalada, Risotto"));
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
