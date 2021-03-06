package com.lambda.UserTicketService.Helpers.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginAckDTO {
    boolean isAuthenticated;
    String token;
}
