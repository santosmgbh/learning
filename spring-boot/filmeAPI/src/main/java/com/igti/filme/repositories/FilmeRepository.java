package com.igti.filme.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.igti.filme.entity.Filme;

public interface FilmeRepository extends MongoRepository<Filme, String>{

}
