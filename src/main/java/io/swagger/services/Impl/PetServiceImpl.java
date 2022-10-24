package io.swagger.services.Impl;

import io.swagger.repository.PetRepository;
import io.swagger.services.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PetServiceImpl implements PetService {
    @Autowired
    PetRepository petRepository;
}
