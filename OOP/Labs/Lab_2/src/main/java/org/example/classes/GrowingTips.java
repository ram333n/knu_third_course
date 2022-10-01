package org.example.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GrowingTips {
    private int temperature;
    private boolean isLightLoving;
    private int waterAmount;
}