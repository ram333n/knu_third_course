package org.example.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VisualParameters {
    private String stemColor;
    private String leavesColor;
    private double averageSize;

    @Override
    public String toString() {
        return String.format("Stem color : %s\n" +
                             "Leaves color : %s\n" +
                             "Water amount : %f",
                             stemColor,
                             leavesColor,
                             averageSize);
    }
}
