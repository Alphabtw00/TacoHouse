package com.example.tacohouse.components;

import com.example.tacohouse.entities.TacoOrder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class SessionScopedTacoOrderManager { //handles the taco order, so we can use same object in both designTaco and manu pages
    private TacoOrder tacoOrder = new TacoOrder();

    public void makeNewTacoOrder(){
        this.tacoOrder= new TacoOrder();
    }
}
