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

import com.example.demo.model.Funcionario;
import com.example.demo.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioResource {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping
	public List<Funcionario> listar(){
		return funcionarioRepository.findAll();
	}
	
	@GetMapping("/show/{id}")
	public ResponseEntity<Funcionario> show(@PathVariable Long id) {
		Optional<Funcionario> funcionario =  funcionarioRepository.findById(id);
		
		if(!funcionario.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(funcionario.get());

	}
	
	@PostMapping("/store")
	public ResponseEntity<Funcionario> criar(@Valid @RequestBody Funcionario funcionario, HttpServletResponse response) {
			Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(funcionarioSalvo.getId()).toUri();
			response.setHeader("Location", uri.toASCIIString());
			
			return ResponseEntity.created(uri).body(funcionarioSalvo);
	}
	
	@DeleteMapping("/delete/{id}")
	public  ResponseEntity<Optional<Funcionario>> delete(@PathVariable Long id, HttpServletResponse response){
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
				
		if(funcionario != null) {
			funcionarioRepository.deleteById(id);
			return ResponseEntity.accepted().body(funcionario);
			
		}else {
			return ResponseEntity.badRequest().body(funcionario);
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Funcionario> update(@RequestBody Funcionario funcionario, @PathVariable long id) {

		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

		if (!funcionarioOptional.isPresent())
			return ResponseEntity.notFound().build();

		funcionario.setId(id);
		
		funcionarioRepository.save(funcionario);

		return ResponseEntity.noContent().build();
	}
}
