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

import com.example.demo.model.Paciente;
import com.example.demo.repository.PacienteRepository;

@RestController
@RequestMapping("/paciente")
public class PacienteResource {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@GetMapping
	public List<Paciente> listar(){
		return pacienteRepository.findAll();
	}
	
	@GetMapping("/show/{id}")
	public ResponseEntity<Paciente> show(@PathVariable Long id){
		Optional<Paciente> paciente =  pacienteRepository.findById(id);
		
		if(!paciente.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(paciente.get());
		
	}
	
	@PostMapping("/store")
	public ResponseEntity<Paciente> criar(@Valid @RequestBody Paciente paciente, HttpServletResponse response){
		Paciente pacienteSalvo = pacienteRepository.save(paciente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(pacienteSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(pacienteSalvo);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Optional<Paciente>> delete(@PathVariable Long id, HttpServletResponse response){
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		
		if(paciente != null) {
			pacienteRepository.deleteById(id);
			return ResponseEntity.accepted().body(paciente);
		} else {
			return ResponseEntity.badRequest().body(paciente);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Paciente> update(@RequestBody Paciente paciente, @PathVariable long id) {

		Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);

		if (!pacienteOptional.isPresent())
			return ResponseEntity.notFound().build();

		paciente.setId(id);
		
		pacienteRepository.save(paciente);

		return ResponseEntity.noContent().build();
	}
}
