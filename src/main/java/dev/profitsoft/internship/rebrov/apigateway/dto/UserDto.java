package dev.profitsoft.internship.rebrov.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String email;
    private String fullName;
}
