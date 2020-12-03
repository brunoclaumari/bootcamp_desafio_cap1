package com.devsuperior.brunocap1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.brunocap1.dto.ClientDTO;
import com.devsuperior.brunocap1.entities.Client;
import com.devsuperior.brunocap1.repositories.ClientRepository;
import com.devsuperior.brunocap1.services.exceptions.DatabaseException;
import com.devsuperior.brunocap1.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageReq) {
		Page<Client> listCli = repository.findAll(pageReq);

		// usando 'map' para que a cada Client da lista
		// seja passado para um ClientDTO
		return listCli.map(cli -> new ClientDTO(cli));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client cli = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Client not found"));

		return new ClientDTO(cli);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client cli = new Client();
		copyDtoToEntity(dto, cli);
		cli = repository.save(cli);

		return new ClientDTO(cli);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client cli = repository.getOne(id);
			copyDtoToEntity(dto, cli);
			cli = repository.save(cli);

			return new ClientDTO(cli);

		} catch (Exception e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation!");
		}

	}

	private void copyDtoToEntity(ClientDTO dto, Client cli) {
		cli.setName(dto.getName());
		cli.setCpf(dto.getCpf());
		cli.setBirthDate(dto.getBirthDate());
		cli.setIncome(dto.getIncome());
		cli.setChildren(dto.getChildren());

	}

}
