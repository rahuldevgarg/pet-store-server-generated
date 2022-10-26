package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.ModelApiResponse;
import io.swagger.model.Pet;
import io.swagger.services.Impl.PetServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-10-21T12:36:17.472Z[GMT]")
@RestController
public class PetApiController implements PetApi {

    private static final Logger log = LoggerFactory.getLogger(PetApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final PetServiceImpl petService;

    @Autowired
    public PetApiController(ObjectMapper objectMapper, HttpServletRequest request, PetServiceImpl petService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.petService = petService;
    }

    public ResponseEntity<Pet> addPet(@Parameter(in = ParameterIn.DEFAULT, description = "Create a new pet in the store", required = true, schema = @Schema()) @Valid @RequestBody Pet body) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Pet pet = petService.addPet(body);
                return new ResponseEntity<Pet>(pet, HttpStatus.OK);
            } catch (DataAccessException e) {
                log.error(e.getMessage());
                return new ResponseEntity<Pet>(HttpStatus.resolve(405));
            }
        }

        return new ResponseEntity<Pet>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deletePet(@Parameter(in = ParameterIn.PATH, description = "Pet id to delete", required = true, schema = @Schema()) @PathVariable("petId") Long petId, @Parameter(in = ParameterIn.HEADER, description = "", schema = @Schema()) @RequestHeader(value = "api_key", required = false) String apiKey) {
        try {
            petService.deletePet(petId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.resolve(400));
        }
    }

    public ResponseEntity<List<Pet>> findPetsByStatus(@Parameter(in = ParameterIn.QUERY, description = "Status values that need to be considered for filter", schema = @Schema(allowableValues = {"available", "pending", "sold"}
            , defaultValue = "available")) @Valid @RequestParam(value = "status", required = false, defaultValue = "available") String status) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            List<Pet> pets = petService.findPetsByStatus(status);
            return new ResponseEntity<List<Pet>>(pets, HttpStatus.OK);
        }

        return new ResponseEntity<List<Pet>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Pet>> findPetsByTags(@Parameter(in = ParameterIn.QUERY, description = "Tags to filter by", schema = @Schema()) @Valid @RequestParam(value = "tags", required = false) List<String> tags) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            List<Pet> pets = petService.findPetsByTags(tags);
            return new ResponseEntity<List<Pet>>(pets, HttpStatus.OK);
        }

        return new ResponseEntity<List<Pet>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Pet> getPetById(@Parameter(in = ParameterIn.PATH, description = "ID of pet to return", required = true, schema = @Schema()) @PathVariable("petId") Long petId) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Pet pet = petService.getPetById(petId);
                return new ResponseEntity<Pet>(pet, HttpStatus.OK);
            } catch (DataAccessException e) {
                log.error(e.getMessage());
                return new ResponseEntity<Pet>(HttpStatus.resolve(404));
            }
        }

        return new ResponseEntity<Pet>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Pet> updatePet(@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent pet in the store", required = true, schema = @Schema()) @Valid @RequestBody Pet body) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Pet pet = petService.updatePet(body);
                return new ResponseEntity<Pet>(pet, HttpStatus.OK);
            } catch (DataAccessException e) {
                log.error(e.getMessage());
                return new ResponseEntity<Pet>(HttpStatus.resolve(404));
            }
        }

        return new ResponseEntity<Pet>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updatePetWithForm(@Parameter(in = ParameterIn.PATH, description = "ID of pet that needs to be updated", required = true, schema = @Schema()) @PathVariable("petId") Long petId, @Parameter(in = ParameterIn.QUERY, description = "Name of pet that needs to be updated", schema = @Schema()) @Valid @RequestParam(value = "name", required = false) String name, @Parameter(in = ParameterIn.QUERY, description = "Status of pet that needs to be updated", schema = @Schema()) @Valid @RequestParam(value = "status", required = false) String status) {
        String accept = request.getHeader("accept");
        try {
            Pet pet = new Pet();
            pet.setId(petId);
            pet.setStatus(Pet.StatusEnum.valueOf(status));
            petService.updatePet(pet);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.resolve(404));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.resolve(404));
        }
    }

    public ResponseEntity<ModelApiResponse> uploadFile(@Parameter(in = ParameterIn.PATH, description = "ID of pet to update", required = true, schema = @Schema()) @PathVariable("petId") Long petId, @Parameter(in = ParameterIn.QUERY, description = "Additional Metadata", schema = @Schema()) @Valid @RequestParam(value = "additionalMetadata", required = false) String additionalMetadata, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ModelApiResponse>(objectMapper.readValue("{\n  \"code\" : 0,\n  \"type\" : \"type\",\n  \"message\" : \"message\"\n}", ModelApiResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ModelApiResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ModelApiResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
