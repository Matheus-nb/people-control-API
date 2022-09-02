package com.api.peoplecontrol.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "TB_PEOPLE")
public class PeopleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public PeopleModel() {
        this.createdAt = LocalDateTime.now(ZoneId.of("UTC"));
        this.updatedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, length = 130)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String profession;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
