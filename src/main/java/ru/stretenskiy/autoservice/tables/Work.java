package ru.stretenskiy.autoservice.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class Work {

    private Long id;
    private Date dateWork;
    private Master master;
    private Car car;
    private Servicing servicing;

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", dateWork=" + dateWork +
                ", master=" + master +
                ", car=" + car +
                ", servicing=" + servicing +
                '}';
    }
}
