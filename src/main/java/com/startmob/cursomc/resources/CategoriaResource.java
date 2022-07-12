package com.startmob.cursomc.resources;

import java.net.URI;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.startmob.cursomc.domain.Categoria;
import com.startmob.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Categoria>> find(@PathVariable Integer id) {
		Optional<Categoria> obj = service.find(id);
		
		System.out.println(obj);
		
		System.out.println(id);
		
		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Categoria> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		System.out.println("insert");
		System.out.println(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id ) {
		
		System.out.println("");
				obj = service.update(obj);
				System.out.println("update");
				System.out.println(obj);
				System.out.println(id);
		return ResponseEntity.noContent().build();
	}
	

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	

	}
	

