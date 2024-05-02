package com.example.tacohouse.repositories;

import com.example.tacohouse.entities.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, String> {
}
