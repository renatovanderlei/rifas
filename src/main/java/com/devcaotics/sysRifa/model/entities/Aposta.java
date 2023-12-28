package com.devcaotics.sysRifa.model.entities;

public class Aposta {
	
	private int codigo;
	private int numero;
	private boolean pago;
	
	public Aposta() {
		this.pago = false;
	}
	
	private Apostador apostador;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}
	
	

}
