package com.api.peoplecontrol.domain.services;

import com.api.peoplecontrol.domain.dtos.PeopleDto;
import com.api.peoplecontrol.domain.error.*;
import com.api.peoplecontrol.domain.models.PeopleModel;
import com.api.peoplecontrol.domain.repositories.PeopleRepository;
import com.api.peoplecontrol.util.ConvertionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    private void validateCpf(String cpf) throws Exception {
        if(this.existsByCpf(cpf)){
            throw new CpfAlreadyExistsException(ErrorMessages.CpfAlreadyExistsExceptionMessage);
        }
    }

    private void validateEmail(String email) throws Exception {
        if(this.existsByEmail(email)){
            throw new EmailAlreadyExistsException(ErrorMessages.EmailAlreadyExistsExceptionMessage);
        }
    }

    private void verifyFields(PeopleDto peopleDto) throws Exception {

        if(ObjectUtils.isEmpty(peopleDto)){
            throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
        }

        if(peopleDto.getName() == null || peopleDto.getName().trim().isEmpty() || peopleDto.getName().isBlank()){
            throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
        }

        if(peopleDto.getAge() == null || peopleDto.getAge().toString().isBlank() || peopleDto.getAge().toString().trim().isEmpty()){
            throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
        }

        if(peopleDto.getCpf() == null || peopleDto.getCpf().isBlank() || peopleDto.getCpf().trim().isEmpty()){
            throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
        }

        if(peopleDto.getProfession() == null || peopleDto.getProfession().isBlank() || peopleDto.getProfession().trim().isEmpty()){
            throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
        }

        if(peopleDto.getEmail() == null || peopleDto.getEmail().isBlank() || peopleDto.getEmail().trim().isEmpty()){
            throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
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

        verifyFields(peopleDto);

        validateCpf(peopleDto.getCpf());
        validateEmail(peopleDto.getEmail());

        PeopleModel personModel = ConvertionUtils.convert(PeopleModel.class, peopleDto);

        return peopleRepository.save(personModel);


    }

    public boolean existsByCpf(String cpf) throws DtoIsEmptyException {
        if(!cpf.isBlank()){
            return peopleRepository.existsByCpf(cpf);
        }

        throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
    }

    public boolean existsByEmail(String email) throws DtoIsEmptyException {
        if(!email.isBlank()){
            return peopleRepository.existsByEmail(email);
        }

        throw new DtoIsEmptyException(ErrorMessages.DtoIsEmpty);
    }

    public List<PeopleModel> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<PeopleModel> findByUuid(UUID uuid){
           return peopleRepository.findById(uuid);
    }
    @Transactional
    public void delete(UUID uuid) throws Exception {

        Optional<PeopleModel> peopleModelOptional = validateIfUuidExists(uuid);

        peopleModelOptional.ifPresent(peopleRepository::delete);
    }

    @Transactional
    public PeopleModel update(UUID uuid, PeopleDto peopleDto) throws Exception {
        verifyFields(peopleDto);

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
