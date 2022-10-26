package io.swagger.services.Impl;

import io.swagger.model.Pet;
import io.swagger.model.Tag;
import io.swagger.repository.PetRepository;
import io.swagger.repository.TagRepository;
import io.swagger.services.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PetServiceImpl implements PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    TagRepository tagRepository;

    @Override
    public Pet addPet(Pet newPet) {
        newPet.setId(null);
        newPet = petRepository.save(newPet);
        newPet = getPetById(newPet.getId());
        return newPet;
    }

    @Override
    public Pet updatePet(Pet updatedPet) throws DataAccessException {
        return petRepository.save(updatedPet);
    }

    @Override
    public Pet getPetById(Long petId) throws DataAccessException{
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.orElse(null);
    }

    @Override
    public void deletePet(Long petId) throws DataAccessException{
        petRepository.deleteById(petId);
    }

    @Override
    public List<Pet> findPetsByStatus(String status) {
        return petRepository.findAllByStatus(Pet.StatusEnum.fromValue(status));
    }

    @Override
    public List<Pet> findPetsByTags(List<String> tags) {
        List<Tag> tagList = tagRepository.findByNameIn(tags);
        return petRepository.findByTagsIn(tagList);
    }
}
