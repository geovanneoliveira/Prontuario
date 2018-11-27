package com.example.demo.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Prontuario;
import com.example.demo.repository.ProntuarioRepository;

@RestController
@RequestMapping("/prontuario")
public class ProntuarioResource {
	
	@Autowired
	private ProntuarioRepository prontuarioRepository;
	
	@GetMapping
	public List<Prontuario> listar(){
		return this.prontuarioRepository.findAll();
	}
	
	@GetMapping("/show/{id}")
	public ResponseEntity<Prontuario> show(@PathVariable Long id){
		Optional<Prontuario> prontuario = prontuarioRepository.findById(id);
		
		if(!prontuario.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(prontuario.get());
	}
	
	@PostMapping("/store")
	public ResponseEntity<Prontuario> salvar(@Valid @RequestBody Prontuario prontuario, HttpServletResponse response){
	
		Prontuario prontuarioSalvar = prontuarioRepository.save(prontuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(prontuarioSalvar.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(prontuarioSalvar);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public  ResponseEntity<Optional<Prontuario>> delete(@PathVariable Long id, HttpServletResponse response){
		Optional<Prontuario> prontuario = prontuarioRepository.findById(id);
				
		if(prontuario != null) {
			prontuarioRepository.deleteById(id);
			return ResponseEntity.accepted().body(prontuario);
			
		}else {
			return ResponseEntity.badRequest().body(prontuario);
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Prontuario> update(@RequestBody Prontuario prontuario, @PathVariable long id) {

		Optional<Prontuario> prontOptional = prontuarioRepository.findById(id);

		if (!prontOptional.isPresent())
			return ResponseEntity.notFound().build();

		prontuario.setId(id);
		
		prontuarioRepository.save(prontuario);

		return ResponseEntity.noContent().build();
	}

}