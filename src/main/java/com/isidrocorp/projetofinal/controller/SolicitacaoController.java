package com.isidrocorp.projetofinal.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isidrocorp.projetofinal.dao.DepartamentoDAO;
import com.isidrocorp.projetofinal.dao.SolicitacaoDAO;
import com.isidrocorp.projetofinal.dao.UsuarioDAO;
import com.isidrocorp.projetofinal.model.Departamento;
import com.isidrocorp.projetofinal.model.Solicitacao;
import com.isidrocorp.projetofinal.model.Usuario;

@RestController
@CrossOrigin("*")
public class SolicitacaoController {
	
	@Autowired
	private SolicitacaoDAO  sdao;
	@Autowired
	private DepartamentoDAO ddao;
	@Autowired
	private UsuarioDAO      udao;
	
	
	@PostMapping("/solicitacoes/nova")
	public ResponseEntity<Solicitacao> adicionarNova(@RequestBody Solicitacao nova){
		try {
			/* aqui se faz a "mágica" */
			System.out.println("SOLICITANTE:"+nova.getSolicitante().getId());
			System.out.println("ORIGEM:"+nova.getOrigem().getId());
			System.out.println("DESTINO:"+nova.getDestino().getId());
			
			Usuario user = udao.findById(nova.getSolicitante().getId()).get();
			
			System.out.println("user = "+user.getNome());
			
			Departamento novo = ddao.findById(nova.getDestino().getId()).get();
			
			String comando = "switchport vlan "+user.getDepartamento().getVlan()+";"+
			                 "interface range "+user.getComputador().getConectorRede()+" "+
					         novo.getVlan()+"; exit";
			
			nova.setComandoRoteador(comando);
			
			user.setDepartamento(novo);
		    
			udao.save(user);
			sdao.save(nova);
			return ResponseEntity.ok(nova);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/solicitacoes/todas")
	public ArrayList<Solicitacao> getall(){
		return (ArrayList<Solicitacao>)sdao.findAll();
	}
	
	@GetMapping("/solicitacoes/{numero}")
	public ResponseEntity<Solicitacao> getDetalhes(@PathVariable int numero){
		Solicitacao sol = sdao.findById(numero).orElse(null);
		if (sol != null) {
			return ResponseEntity.ok(sol);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
