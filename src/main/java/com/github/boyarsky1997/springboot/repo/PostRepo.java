package com.github.boyarsky1997.springboot.repo;

import com.github.boyarsky1997.springboot.models.Client;
import com.github.boyarsky1997.springboot.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepo extends CrudRepository<Post, Integer> {

}
