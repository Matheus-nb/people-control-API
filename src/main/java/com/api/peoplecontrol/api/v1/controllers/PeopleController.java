package com.api.peoplecontrol.api.v1.controllers;

import com.api.peoplecontrol.domain.dtos.PeopleDto;
import com.api.peoplecontrol.domain.error.ErrorMessages;
import com.api.peoplecontrol.domain.error.UuidNotFoundException;
import com.api.peoplecontrol.domain.models.PeopleModel;
import com.api.peoplecontrol.domain.services.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/people/v1")
public class PeopleController {

    final
    PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@RequestBody @Valid PeopleDto peopleDto) throws Exception {

        PeopleModel personModel = peopleService.save(peopleDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(personModel);
    }

    @GetMapping
    public ResponseEntity<List<PeopleModel>> getAllPeople() {

        List<PeopleModel> peopleModelList = peopleService.findAll();

        if(peopleModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(peopleModelList);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> getPerson(@PathVariable(value = "uuid") UUID uuid) throws UuidNotFoundException {

        Optional<PeopleModel> peopleModelOptional = peopleService.findByUuid(uuid);

        if(peopleModelOptional.isEmpty()){
            throw new UuidNotFoundException(ErrorMessages.UuidNotFound);
        }

        return ResponseEntity.status(HttpStatus.OK).body(peopleModelOptional.get());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "uuid") UUID uuid,
                                               @RequestBody @Valid PeopleDto peopleDto) throws Exception {

        PeopleModel peopleModel = peopleService.update(uuid, peopleDto);

        return ResponseEntity.status(HttpStatus.OK).body(peopleModel);

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "uuid") UUID uuid) throws Exception {
        peopleService.delete(uuid);

        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
    }
}
