package org.example.messenger.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ChatCreateRequest {
    private List<Long> participantIds; // Список ID участников
}