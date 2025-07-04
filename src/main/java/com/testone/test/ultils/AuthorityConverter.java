package com.testone.test.ultils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public class AuthorityConverter {

    public String convertAuthoritiesToString(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return ""; // Or handle as needed, e.g., return null
        }
        return authorities.stream()
                .map(GrantedAuthority::getAuthority) // Get the string representation of each authority
                .collect(Collectors.joining(", ")); // Join them with a comma and space
    }

    public Collection<? extends GrantedAuthority> convertStringToCollectionAuthority(String strCollect){
        String authorityString = "ROLE_ADMIN,ROLE_USER,PERMISSION_READ";
        return AuthorityUtils.commaSeparatedStringToAuthorityList(strCollect);
    }
}
