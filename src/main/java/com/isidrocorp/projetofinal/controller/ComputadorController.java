package com.isidrocorp.projetofinal.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isidrocorp.projetofinal.dao.ComputadorDAO;
import com.isidrocorp.projetofinal.model.Computador;

@RestController
@CrossOrigin("*")
public class ComputadorController {
	@Autowired
	private ComputadorDAO cao;
	
	@GetMapping("/computadores")
	public ArrayList<Computador> buscarTodos(){
		ArrayList<Computador> lista;
		lista = (ArrayList<Computador>)cao.findAll();
		return lista;
	}
	@GetMapping("/computador/{id}")
	public ResponseEntity<Computador> buscarPorId(@PathVariable int id){
		Computador comp = cao.findById(id).orElse(null);
		if(comp != null) {
			return ResponseEntity.ok(comp);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	@PutMapping("/computador/novo")
	public ResponseEntity<Computador> adicionarComputador(@RequestBody Computador novoComp){
		try {
			cao.save(novoComp);
			return ResponseEntity.ok(novoComp);
		} catch(Exception ex) {
			return ResponseEntity.status(400).build();
	}
	}
	
	}

