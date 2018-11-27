package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Prontuario;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

}
