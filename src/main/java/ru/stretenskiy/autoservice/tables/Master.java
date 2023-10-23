package ru.stretenskiy.autoservice.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Master {

    private Long id;
    private String name;

    public Master(String name) {
        this.name = name;
    }

    public void setValue(Master master) {
        name = master.name.isEmpty() ? name : master.name;
    }

    @Override
    public String toString() {
        return "\nMaster{" +
                "id = " + id +
                "\nname = '" + name + '\'' +
                '}';
    }
}
