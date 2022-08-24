package com.api.peoplecontrol.domain.services;

import com.api.peoplecontrol.domain.dtos.PeopleDto;
import com.api.peoplecontrol.domain.models.PeopleModel;
import com.api.peoplecontrol.domain.repositories.PeopleRepository;
import com.api.peoplecontrol.domain.error.CpfAlreadyExistsException;
import com.api.peoplecontrol.domain.error.EmailAlreadyExistsException;
import com.api.peoplecontrol.domain.error.ErrorMessages;
import com.api.peoplecontrol.domain.error.UuidNotFoundException;
import com.api.peoplecontrol.util.ConvertionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PeopleService {

    final
    PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    private void validateCpfAndEmail(PeopleDto peopleDto) throws Exception {
        if(this.existsByCpf(peopleDto.getCpf())){
            throw new CpfAlreadyExistsException(ErrorMessages.CpfAlreadyExistsExceptionMessage);
        }

        if(this.existsByEmail(peopleDto.getEmail())){
            throw new EmailAlreadyExistsException(ErrorMessages.EmailAlreadyExistsExceptionMessage);
        }
    }

    private Optional<PeopleModel> validateIfUuidExists(UUID uuid) throws Exception {
        Optional<PeopleModel> peopleModelOptional = this.findByUuid(uuid);

        if(peopleModelOptional.isEmpty()){
            throw new UuidNotFoundException(ErrorMessages.UuidNotFound);
        }

        return peopleModelOptional;
    }

    @Transactional
    public PeopleModel save(PeopleDto peopleDto) throws Exception {

        validateCpfAndEmail(peopleDto);

        PeopleModel personModel = ConvertionUtils.convert(PeopleModel.class, peopleDto);

        personModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        personModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return peopleRepository.save(personModel);
    }

    public boolean existsByCpf(String cpf) {
        return peopleRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return peopleRepository.existsByEmail(email);
    }

    public List<PeopleModel> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<PeopleModel> findByUuid(UUID uuid) {
        return peopleRepository.findById(uuid);
    }
    @Transactional
    public void delete(UUID uuid) throws Exception {
        Optional<PeopleModel> peopleModelOptional = validateIfUuidExists(uuid);

        peopleModelOptional.ifPresent(peopleRepository::delete);
    }

    @Transactional
    public PeopleModel update(UUID uuid, PeopleDto peopleDto) throws Exception {

        Optional<PeopleModel> peopleModelOptional = validateIfUuidExists(uuid);

        PeopleModel personModel = ConvertionUtils.convert(PeopleModel.class, peopleDto);

        if(peopleModelOptional.isPresent()){
            personModel.setUuid(peopleModelOptional.get().getUuid());
            personModel.setCreatedAt(peopleModelOptional.get().getCreatedAt());
        }

        personModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return peopleRepository.save(personModel);
    }
}
