package com.api.peoplecontrol.services;

import com.api.peoplecontrol.models.PeopleModel;
import com.api.peoplecontrol.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public PeopleModel save(PeopleModel personModel) {
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

    public void delete(PeopleModel peopleModel) {
        peopleRepository.delete(peopleModel);
    }
}
