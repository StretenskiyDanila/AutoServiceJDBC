package ru.stretenskiy.autoservice.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class MasterCountCars {

    private String name;
    private Long countCars;

}
