package dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayerDto {
    private Long id;
    private Long teamId;
    private String name;
    private BigDecimal price;
}
