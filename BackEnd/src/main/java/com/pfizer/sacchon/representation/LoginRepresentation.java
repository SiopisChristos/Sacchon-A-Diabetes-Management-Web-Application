package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.UserTable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRepresentation {
    private String username;
    private String password;
    private String role;

    public LoginRepresentation(UserTable userTable){
        if (userTable != null){
            username = userTable.getUsername();
            password = userTable.getPassword();
            role = userTable.getRole();
        }
    }

    public UserTable createUser(){
        UserTable user = new UserTable();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }




}
