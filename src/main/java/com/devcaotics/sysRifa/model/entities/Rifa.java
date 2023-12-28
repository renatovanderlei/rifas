package com.devcaotics.sysRifa.model.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Rifa {
	
	private int codigo;
	private long dataCriacao;
	private long dataSorteio;
	private double valorAposta;
	private int quantNumeros;
	private String status;
	
	private List<Premio> premios;
	private List<Aposta> apostas;
	private List<Aposta> vencedores;
	
	public Rifa() {
		this.dataCriacao = System.currentTimeMillis();
		this.apostas = new ArrayList<>();
		this.vencedores = new ArrayList<>();
		this.status = "ABERTO";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public long getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(long dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public long getDataSorteio() {
		return dataSorteio;
	}

	public void setDataSorteio(long dataSorteio) {
		this.dataSorteio = dataSorteio;
	}

	public double getValorAposta() {
		return valorAposta;
	}

	public void setValorAposta(double valorAposta) {
		this.valorAposta = valorAposta;
	}

	public int getQuantNumeros() {
		return quantNumeros;
	}

	public void setQuantNumeros(int quantNumeros) {
		this.quantNumeros = quantNumeros;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public List<Premio> getPremios() {
		return premios;
	}

	public void setPremios(List<Premio> premios) {
		this.premios = premios;
	}

	public List<Aposta> getApostas() {
		return apostas;
	}

	public List<Aposta> getVencedores() {
		return vencedores;
	}
	
	public String getStatus() {
		return status;
	}
	

	/*
	 * Parte dos comportamentos
	 */
	
	public void addAposta(Aposta aposta) {
		
		if(this.apostas.isEmpty()) {
			this.apostas.add(aposta);
			return;
		}
		
		for(int i = 0; i< this.apostas.size(); i++) {
			
			if(this.apostas.get(i).getNumero()>aposta.getNumero()) {
				this.apostas.add(i, aposta);
				
				if(this.apostas.size() == quantNumeros) {
					this.status = "fechado";
				}
				
				break;
				
			}
			
		}
		
	}
	
	public String getDataCriacaoFormated() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dataCriacao);
	}
	
	public String getDataSorteioFormated() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dataSorteio);
	}
	
	public void sortear(int numVencedores[]) {
		
		for(int i = 0; i<numVencedores.length; i++) {
			
			this.vencedores.add(this.apostas.get(numVencedores[i]));
			
		}
		
		this.setDataCriacao(System.currentTimeMillis());
		this.status = "sorteado";
		
	}

}
