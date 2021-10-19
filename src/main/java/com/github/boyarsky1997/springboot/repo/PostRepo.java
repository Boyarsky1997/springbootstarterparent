package com.github.boyarsky1997.springboot.repo;

import com.github.boyarsky1997.springboot.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepo extends CrudRepository<Post, Integer> {

    @Modifying
//    @Query(value = "UPDATE posts SET views = views +1 WHERE post_id = :postId", nativeQuery = true)
    @Query("UPDATE Post t SET t.views = t.views + 1 WHERE t.postId = :postId")
    void incrementViews(@Param("postId") int postId);
}
