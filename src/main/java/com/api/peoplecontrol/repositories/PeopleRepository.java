package com.api.peoplecontrol.repositories;

import com.api.peoplecontrol.models.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleModel, UUID> {


    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
