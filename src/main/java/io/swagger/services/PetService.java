package io.swagger.services;

import io.swagger.model.ModelApiResponse;
import io.swagger.model.Pet;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PetService {
    Pet addPet(Pet newPet);
    Pet updatePet(Pet updatedPet) throws DataAccessException;
    Pet getPetById(Long petId) throws DataAccessException;
    void deletePet(Long petId) throws DataAccessException;
    List<Pet> findPetsByStatus(String status);
    List<Pet> findPetsByTags(List<String> tags);
}
