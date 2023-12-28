package com.devcaotics.sysRifa.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devcaotics.sysRifa.model.entities.Apostador;
import com.devcaotics.sysRifa.model.repositories.ApostadorRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/apostador")
public class ApostadorController {
	
	@Autowired
	private ApostadorRepository ApostadorRepository;
	
	@PostMapping
	public String create(@RequestBody Apostador a) {
		
		System.out.println("Teste");
		try {
			//RepositoryService.getCurrentInstance().create(a);
			ApostadorRepository.create(a);
			return "Apostador cadastrado com sucesso!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "falha ao cadastrar um apostador";
		}
		
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Apostador a) {
		
		try {
			//RepositoryService.getCurrentInstance().update(a);
			ApostadorRepository.update(a, null);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{codigo}")
	public Apostador read(@PathVariable("codigo") int codigo) {
		
		try {
			//return RepositoryService.getCurrentInstance().read(codigo);
			
			return ApostadorRepository.read(codigo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> delete(@PathVariable int codigo){
		
		try {
			//RepositoryService.getCurrentInstance().delete(codigo);
			ApostadorRepository.delete(codigo);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping
	public ResponseEntity<List<Apostador>> read(){
		
		try {
			//List<Apostador> apostadores = RepositoryService.getCurrentInstance().readAll();
			List<Apostador> apostadores = ApostadorRepository.readAll();
			
			return new ResponseEntity<List<Apostador>>(apostadores,HttpStatus.OK);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.OK);
		}
		

	}
	
}
