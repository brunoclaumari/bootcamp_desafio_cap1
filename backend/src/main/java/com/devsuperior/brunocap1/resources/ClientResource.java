package com.devsuperior.brunocap1.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.brunocap1.dto.ClientDTO;
import com.devsuperior.brunocap1.services.ClientService;

//Classe implementa os elementos REST
//da entidade Client

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy// vai ordenar pelo atributo 'name' da
																					// classe Product
	) {		
		// Esses 4 annotations RequestParams são os parametros de paginação da aplicação
		PageRequest pageReq = PageRequest
				.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ClientDTO> pageListDto = service.findAllPaged(pageReq);
		
		return ResponseEntity.ok().body(pageListDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		
		ClientDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);		
	}
	
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto){
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> delete(@PathVariable Long id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		// vai retornar 204 que é que deu certo e o corpo da
		// resposta está vazio.		
	}
	
	
	
	

}
