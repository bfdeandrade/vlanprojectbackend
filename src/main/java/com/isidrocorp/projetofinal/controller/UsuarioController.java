package com.isidrocorp.projetofinal.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isidrocorp.projetofinal.dao.ComputadorDAO;
import com.isidrocorp.projetofinal.dao.UsuarioDAO;
import com.isidrocorp.projetofinal.model.Computador;
import com.isidrocorp.projetofinal.model.Usuario;

@RestController
@CrossOrigin("*")
public class UsuarioController {
	
	@Autowired
	private UsuarioDAO dao;
	@Autowired 
	private ComputadorDAO compDao;
	
	@GetMapping("/usuarios")
	public ArrayList<Usuario> listarTodos(){
		return (ArrayList<Usuario>)dao.findAll();
	}
	
	@SuppressWarnings("unused")
	@PostMapping("/login")
	public ResponseEntity<Usuario> logarUsuario(@RequestBody Usuario dadosLogin) {		
		Usuario res = dao.findByEmail(dadosLogin.getEmail());
		
	    if (res != null) {  // o usuario existe!!!
	    	if (res.getSenha().equals(dadosLogin.getSenha())) {
	    	   res.setSenha("****");
	    	
 	    	   return ResponseEntity.ok(res);
	    	}
	    	else {
	    	   return ResponseEntity.status(403).build();
	    	}
	    }
	    else {     // o usuario NÃO existe!!!
	    	return ResponseEntity.status(404).build();
	    }
	} 
	
	@PutMapping("/usuario/update")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario novosDados){
		try {
			dao.save(novosDados);
			return ResponseEntity.ok(novosDados);
		}
		catch(Exception ex) {
			return ResponseEntity.status(400).build();
		}
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> buscarPeloId(@PathVariable int id){
		Usuario user = dao.findById(id).orElse(null);
		if (user != null) {
			return ResponseEntity.ok(user);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	@PutMapping("/usuario/novo")
	public ResponseEntity<Usuario> adicionarUsuario(@RequestBody Usuario novoDados){
		try {
			dao.save(novoDados);
			return ResponseEntity.ok(novoDados);
		}
		catch(Exception ex) {
				return ResponseEntity.status(400).build();
			}
		}
}




