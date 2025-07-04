package com.testone.test.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor

public class JwtAuthenticationresponse {
    String token;
    String email;
    String firstName;
    String lastName;
    Long id;
}
