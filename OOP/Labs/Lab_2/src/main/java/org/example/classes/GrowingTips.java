package org.example.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrowingTips {
    private int temperature;
    private boolean isLightLoving;
    private int waterAmount;

    @Override
    public String toString() {
        return String.format("Temperature : %d\n" +
                             "Is light-loving : %s\n" +
                             "Water amount : %d",
                             temperature,
                             isLightLoving,
                             waterAmount);
    }
}