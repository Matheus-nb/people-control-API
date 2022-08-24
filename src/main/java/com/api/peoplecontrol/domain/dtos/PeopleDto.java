package com.api.peoplecontrol.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PeopleDto {

    @NotBlank
    @Size(min = 14, max = 14)
    private String cpf;

    @NotBlank
    private String name;

    @NotNull
    private int age;

    @NotBlank
    private String email;

    @NotBlank
    private String profession;
}
