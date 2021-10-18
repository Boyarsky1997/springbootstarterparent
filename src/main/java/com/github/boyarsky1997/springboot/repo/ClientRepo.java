package com.github.boyarsky1997.springboot.repo;

import com.github.boyarsky1997.springboot.models.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepo extends CrudRepository<Client,Integer> {
    Optional<Client> findByLogin(String login);
}
