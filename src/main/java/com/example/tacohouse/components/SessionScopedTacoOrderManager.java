package com.example.tacohouse.components;

import com.example.tacohouse.entities.TacoOrder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class SessionScopedTacoOrderManager {
    private final TacoOrder tacoOrder = new TacoOrder();
}
