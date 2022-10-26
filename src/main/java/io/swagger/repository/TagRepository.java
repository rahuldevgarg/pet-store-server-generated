package io.swagger.repository;

import io.swagger.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String tag);
    List<Tag> findByNameIn(List<String> tags);
}
