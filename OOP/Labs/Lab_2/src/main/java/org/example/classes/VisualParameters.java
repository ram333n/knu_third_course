package org.example.classes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
