package dev.profitsoft.internship.rebrov.apigateway.controller;

import dev.profitsoft.internship.rebrov.apigateway.dto.UserDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @GetMapping("/profile")
    public UserDto getCurrentUser(@AuthenticationPrincipal OidcUser user) {
        return new UserDto(user.getEmail(), user.getFullName());
    }
}
