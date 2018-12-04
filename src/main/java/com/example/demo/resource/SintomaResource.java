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
import com.example.demo.model.Sintoma;
import com.example.demo.repository.SintomaRepository;

@RestController
@RequestMapping("/sintoma")
public class SintomaResource {
	
	@Autowired
	private SintomaRepository sintomaRespository;
	
	@GetMapping
	public List<Sintoma> listar(){
		return sintomaRespository.findAll();
	}
	
	@GetMapping("/show/{id}")
	public ResponseEntity<Sintoma> show(@PathVariable Long id){
		Optional<Sintoma> sintoma =  sintomaRespository.findById(id);
		
		if(!sintoma.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(sintoma.get());
		
	}
	
	@PostMapping("/store")
	public ResponseEntity<Sintoma> criar(@Valid @RequestBody Sintoma sintoma, HttpServletResponse response){
		Sintoma sintomaSalvo = sintomaRespository.save(sintoma);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(sintomaSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(sintomaSalvo);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Optional<Sintoma>> delete(@PathVariable Long id, HttpServletResponse response){
		Optional<Sintoma> sintoma = sintomaRespository.findById(id);
		
		if(sintoma != null) {
			sintomaRespository.deleteById(id);
			return ResponseEntity.accepted().body(sintoma);
		} else {
			return ResponseEntity.badRequest().body(sintoma);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Sintoma> update(@RequestBody Sintoma sintoma, @PathVariable long id) {

		Optional<Sintoma> sintomaOptional = sintomaRespository.findById(id);

		if (!sintomaOptional.isPresent())
			return ResponseEntity.notFound().build();

		sintoma.setId(id);
		
		sintomaRespository.save(sintoma);

		return ResponseEntity.noContent().build();
	}
}
