package domain;

import java.time.LocalDate;

public class Reserva {

	private Cliente cliente;
	private LocalDate fecha;
	private String hora;
	private int personas;
	private Mesa mesa;
	private EstadoReserva estado;

	public Reserva(Cliente cliente, LocalDate fecha, String hora, int personas, Mesa mesa) {
		this.cliente = cliente;
		this.fecha = fecha;
		this.hora = hora;
		this.personas = personas;
		this.mesa = mesa;
		this.estado = EstadoReserva.PENDIENTE;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public int getPersonas() {
		return personas;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
