package com.devcaotics.sysRifa.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devcaotics.sysRifa.model.entities.Premio;
import com.devcaotics.sysRifa.model.repositories.PremioRepository;
import com.devcaotics.sysRifa.model.repositories.RifaRepository;

import PremiosDTO.PremiosDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/premios")
public class PremioController {
	
	@Autowired
	private PremioRepository PremioRepository;
	
	@Autowired
	private RifaRepository RifaRepository;
	
	@GetMapping("/{codigo}")
	public Premio read(@PathVariable("codigo") int codigo) {
		
		try {
			//return RepositoryService.getCurrentInstance().read(codigo);
			
			return PremioRepository.read(codigo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping
	public ResponseEntity<List<PremiosDTO>> read(){
		
		try {
			List<PremiosDTO> listaPremiosDTO = new ArrayList<PremiosDTO>();
			
			List<Premio> premios = PremioRepository.readAll();
			//Aqui, vou criar uma lista pegando o código da rifa e o status de acordo com o código da rifa
			for (Premio premio : premios) {
				var premioDTO = new PremiosDTO();
				premioDTO.setCodigo(premio.getCodigo());
				premioDTO.setDescricao(premio.getDescricao());
				premioDTO.setCodigo_rifa(premio.getCodigo_rifa());
				premioDTO.setStatus(RifaRepository.read(premio.getCodigo_rifa()).getStatus());
				listaPremiosDTO.add(premioDTO); //estou tratando os dados salvos das duas entidades,
				//DTO pega as informações do banco de dados de entidades distintas e junta como eu quiser mostrar.
			}
			
			return new ResponseEntity<List<PremiosDTO>>(listaPremiosDTO,HttpStatus.OK);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.OK);
		}
		

	}
}
