package io.swagger.repository;

import io.swagger.model.Pet;
import io.swagger.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findAllByStatus(Pet.StatusEnum status);
    List<Pet> findByTagsIn(List<Tag> tags);
}
