package com.pfizer.sacchon.security.dao;


import com.pfizer.sacchon.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser{
    private String username;
    private String password;
    private Role role;
    private Boolean isActive;

}