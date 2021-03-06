package com.lambda.EventService.Models.Api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BuyATicketDTO {
    private Long eventId;
    private Long userId;
    private Long numberOfTickets;
}
