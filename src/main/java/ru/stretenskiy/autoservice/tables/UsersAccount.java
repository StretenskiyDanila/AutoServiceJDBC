package ru.stretenskiy.autoservice.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsersAccount {

    private Long id;
    private String userLogin;
    private String userPsw;

    public UsersAccount(String userLogin, String userPsw) {
        this.userLogin = userLogin;
        this.userPsw = userPsw;
    }

}
