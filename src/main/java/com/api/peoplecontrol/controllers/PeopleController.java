package com.api.peoplecontrol.controllers;

import com.api.peoplecontrol.dtos.PeopleDto;
import com.api.peoplecontrol.models.PeopleModel;
import com.api.peoplecontrol.services.PeopleService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/people")
public class PeopleController {

    final
    PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@RequestBody @Valid PeopleDto peopleDto) {

        if(peopleService.existsByCpf(peopleDto.getCpf())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já está em uso!");
        }

        if(peopleService.existsByEmail(peopleDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já está em uso!");
        }

        var personModel = new PeopleModel();

        BeanUtils.copyProperties(peopleDto, personModel);
        personModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        personModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(peopleService.save(personModel));

    }

    @GetMapping
    public ResponseEntity<List<PeopleModel>> getAllPeople() {
        return ResponseEntity.status(HttpStatus.OK).body(peopleService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> getPerson(@PathVariable(value = "uuid") UUID uuid){

        Optional<PeopleModel> peopleModelOptional = peopleService.findByUuid(uuid);

        if(peopleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UUID não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(peopleModelOptional.get());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "uuid") UUID uuid, @RequestBody @Valid PeopleDto peopleDto){

        Optional<PeopleModel> peopleModelOptional = peopleService.findByUuid(uuid);

        if(peopleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UUID não encontrado");
        }

        var peopleModel = new PeopleModel();

        BeanUtils.copyProperties(peopleDto, peopleModel);

        peopleModel.setUuid(peopleModelOptional.get().getUuid());
        peopleModel.setCreatedAt(peopleModelOptional.get().getCreatedAt());
        peopleModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.OK).body(peopleService.save(peopleModel));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "uuid") UUID uuid){
        Optional<PeopleModel> peopleModelOptional = peopleService.findByUuid(uuid);

        if(peopleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UUID não encontrado");
        }

        peopleService.delete(peopleModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
    }
}
