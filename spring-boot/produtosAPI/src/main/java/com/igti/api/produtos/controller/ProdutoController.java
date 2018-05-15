package com.igti.api.produtos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.igti.api.produtos.entity.Produto;
import com.igti.api.produtos.repositories.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Produto>> listar(){
		List<Produto> produtos = produtoRepository.findAll();
		
		return new ResponseEntity<>(produtos, HttpStatus.OK);	
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto){		
		produto = produtoRepository.save(produto);		
		return new ResponseEntity<>(produto, HttpStatus.OK); 
	}
	
		@RequestMapping(method=RequestMethod.GET, path="findByNome/{nome}")
		public ResponseEntity<Produto> findByNome(@PathVariable("nome") String nome){		
			Produto produto = produtoRepository.findByNome(nome);
			return new ResponseEntity<>(produto, HttpStatus.OK); 
		}
	
	@RequestMapping(method=RequestMethod.GET, path="findById/{id}")
	public ResponseEntity<Produto> findById(@PathVariable("id") String id){		
		Produto produto = produtoRepository.findById(id).get();
		return new ResponseEntity<>(produto, HttpStatus.OK); 
	}
	
	@RequestMapping(method=RequestMethod.DELETE, path="{id}")
	public ResponseEntity<String> remove(@PathVariable("id") String id){
		try {
			Produto produto = produtoRepository.findById(id).get();
			produtoRepository.delete(produto);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT); 	
		}
		return new ResponseEntity<>(id, HttpStatus.OK);
		
	}
}
