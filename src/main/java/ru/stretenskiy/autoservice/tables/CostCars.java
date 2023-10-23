package ru.stretenskiy.autoservice.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CostCars {

    private BigDecimal allCostOur;
    private BigDecimal allCostForeign;

}
