package ru.stretenskiy.autoservice.tables;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Servicing {

    private Long id;
    private String name;
    private BigDecimal costOur;
    private BigDecimal costForeign;

    public Servicing(String name, BigDecimal costOur, BigDecimal costForeign) {
        this.name = name;
        this.costOur = costOur;
        this.costForeign = costForeign;
    }

    public void setValue (Servicing servicing) {
        name = servicing.name.isEmpty() ? name : servicing.name;
        costOur = servicing.costOur == null ? costOur : servicing.costOur;
        costForeign = servicing.costForeign == null ? costForeign : servicing.costForeign;
    }
}
