package br.com.almir.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="USER")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NOME", nullable=false)
	private String nome;
		
	@Column(name="LOGIN", nullable=false)
	private String login;
		
	@Column(name="SENHA", nullable=false)
	private String senha;	

	@Transient
	private String keyAuthentication;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getKeyAuthentication() {
		return keyAuthentication;
	}

	public void setKeyAuthentication(String keyAuthentication) {
		this.keyAuthentication = keyAuthentication;
	}


	
	


	


}
