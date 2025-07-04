package com.testone.test.model;

import jakarta.persistence.Entity;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserToken extends User_ {
    private String token;
    private Boolean registered;
}
