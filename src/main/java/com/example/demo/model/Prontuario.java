package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="prontuario")
public class Prontuario implements Serializable {

	private static final long serialVersionUID = 6053684601651228583L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String doenca;
	private String diagnostico;
	private String medicacao;
	private String status;
	@Column(name = "data_entrada", nullable = false)
	private Date dataEntrada;
	@Column(name = "data_saida")
	private Date dataSaida;
	
	@OneToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;
	
	@OneToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
		
	/**
	 * commented into many to many annotation
	 *  
	 *  cascade = {
	 *              CascadeType.PERSIST,
	 *              CascadeType.MERGE
	 *           }
	 * 
	 * **/
	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "prontuario_sintoma",
	            joinColumns = { @JoinColumn(name = "id_prontuario") },
	            inverseJoinColumns = { @JoinColumn(name = "id_sintoma") })
	    private Set<Sintoma> sintomas = new HashSet<>();
	 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDoenca() {
		return doenca;
	}
	public void setDoenca(String doenca) {
		this.doenca = doenca;
	}
	public String getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	public String getMedicacao() {
		return medicacao;
	}
	public void setMedicacao(String medicacao) {
		this.medicacao = medicacao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public Date getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Set<Sintoma> getSintomas() {
		return sintomas;
	}
	public void setSintomas(Set<Sintoma> sintomas) {
		this.sintomas = sintomas;
	}
	
}
