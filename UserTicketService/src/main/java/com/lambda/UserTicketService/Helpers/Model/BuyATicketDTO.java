package com.lambda.UserTicketService.Helpers.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BuyATicketDTO {
    private long eventId;
    private long userId;
    private long numberOfTickets;
}
