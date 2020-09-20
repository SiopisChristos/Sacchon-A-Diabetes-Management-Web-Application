package com.pfizer.sacchon.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserTable {
    @Id
    private String username; /** primary key */
    private String password;
    private String role;

}
