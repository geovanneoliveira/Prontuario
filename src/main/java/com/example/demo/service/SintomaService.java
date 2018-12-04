package com.example.demo.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Prontuario;
import com.example.demo.model.Sintoma;
import com.example.demo.repository.SintomaRepository;

public class SintomaService {
	
	@Autowired
	private SintomaRepository sintomaRepository;
	
	public Prontuario configurarSugestoes (Prontuario prontuario) {
		
		List<Sintoma> sintomasBanco = sintomaRepository.findAll();
		HashSet<Sintoma> sintomas = new HashSet<>();
		
		for(Sintoma sintoma : sintomasBanco) {
			for(Sintoma sintomaCorrente : prontuario.getSintomas()) {
				if(sintomaCorrente.getSintoma().equalsIgnoreCase(sintoma.getSintoma())) {
					sintomas.add(sintoma);
				}
			}
		}
		
		prontuario.setSintomas(sintomas);
		
		return prontuario;
	}

}
