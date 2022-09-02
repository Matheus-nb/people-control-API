package com.api.peoplecontrol.domain.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeopleDto {

    @NotEmpty(message = "CPF necessário")
    @Size(min = 14, max = 14)
    private String cpf;

    @NotEmpty(message = "Nome necessário")
    private String name;

    @NotNull(message = "Idade necessária")
    private Integer age;

    @NotEmpty(message = "E-mail necessário")
    private String email;

    @NotEmpty(message = "Profissão necessária")
    private String profession;
}
