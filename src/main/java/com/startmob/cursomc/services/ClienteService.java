package com.startmob.cursomc.services;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.startmob.cursomc.domain.Cliente;
import com.startmob.cursomc.dto.ClienteDTO;
import com.startmob.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName(), null);
			 
	}

	return obj.get();
}
	

	public Cliente update(Cliente obj) {
		 Cliente newObj = findAll(obj.getId());
		return repo.save(obj);
	}




	public void delete(Integer id) {
		findAll(id);
		try {
		repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e){
		}
	}
	
	private Cliente findAll(Integer id) {
		return null;
		// TODO Auto-generated method stub
		
	}


	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Cliente fromDTO(ClienteDTO objDto ) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(),null, null);
	}
	
	
}
	

