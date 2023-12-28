package com.devcaotics.sysRifa.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devcaotics.sysRifa.model.entities.Aposta;
import com.devcaotics.sysRifa.model.entities.Rifa;
import com.devcaotics.sysRifa.model.repositories.RifaRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/rifa")
public class RifaController {

	@Autowired
	private RifaRepository RifaRepository;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Rifa r){
		
		try {
			//RepositoryService.getCurrentInstance().create(r);
			RifaRepository.create(r);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/{codigo}/aposta")
	public ResponseEntity<?> realizarAposta(@RequestBody Aposta a,@PathVariable("codigo") int codigoRifa){
		
		Rifa r;
		try {
			//r = RepositoryService.getCurrentInstance().readRifa(codigoRifa);
			r = RifaRepository.read(codigoRifa);
			for(Aposta aAux: r.getApostas()){
				if(aAux.getNumero() == a.getNumero()) {
					return ResponseEntity.internalServerError().body("aposta já cadastrada");
				}
			}
			
			r.addAposta(a);
			
			RifaRepository.addAposta(r, a);
			//RepositoryService.getCurrentInstance().addAposta(r, a);
			
			if(r.getStatus().equals("fechado")) {
				return ResponseEntity.internalServerError().build();
			}
		
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.OK);
		}
		
		
	}
	
	@PostMapping("/pagar")
	public ResponseEntity<?> pagarAposta(@RequestBody Aposta a){
		
		a.setPago(true);
		try {
			//RepositoryService.getCurrentInstance().pagarAposta(a);
			RifaRepository.pagarAposta(a);
			return ResponseEntity.ok().build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.OK);
		}
		
	}
	
	@GetMapping
	public ResponseEntity<List<Rifa>> readRifas(){
		
		try {
			//List<Rifa> rifas = RepositoryService.getCurrentInstance().readAllRifas();
			return new ResponseEntity<List<Rifa>>(RifaRepository.readAll(),HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
