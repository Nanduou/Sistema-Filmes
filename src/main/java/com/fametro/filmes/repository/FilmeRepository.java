package com.fametro.filmes.repository;

import com.fametro.filmes.model.Filme;
import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends CrudRepository<Filme, Long> {
}
