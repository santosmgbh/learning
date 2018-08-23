package com.igti.api.produtos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.igti.api.produtos.entity.Produto;

public interface ProdutoRepository extends MongoRepository<Produto, String>{

	Produto findByNome(String nome);

}
