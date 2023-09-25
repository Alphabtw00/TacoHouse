package com.example.tacohouse.repositories;

import com.example.tacohouse.entities.PreMadeTaco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreMadeTacoRepository extends CrudRepository<PreMadeTaco,String> {
}
