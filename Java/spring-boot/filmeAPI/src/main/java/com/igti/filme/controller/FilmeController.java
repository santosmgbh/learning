package com.igti.filme.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.igti.filme.entity.Filme;
import com.igti.filme.repositories.FilmeRepository;

@RestController
@RequestMapping("/filmes")
public class FilmeController {
	
	@Autowired
	private FilmeRepository filmeRepository;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Filme>> listar(){
		List<Filme> filmes = filmeRepository.findAll();
//		List<Filme> filmes = new ArrayList<Filme>();
//		Filme f = new Filme();
//		f.setAno(1900);
//		f.setDiretor("123123");
//		f.setGenero("asdasd");
//		f.setTitulo("12312asdasdasd");
//		filmes.add(f);
		
		return new ResponseEntity<>(filmes, HttpStatus.OK);
		
	}
}
