package ru.stretenskiy.autoservice.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Car {

    private Long id;
    private String carNumber;
    private String carColor;
    private String carMark;
    private Integer isForeign;

    public Car(String carNumber, String carColor, String carMark, Integer isForeign) {
        this.carNumber = carNumber;
        this.carColor = carColor;
        this.carMark = carMark;
        this.isForeign = isForeign;
    }

    public void setValue(Car car) {
        carNumber = car.carNumber.isEmpty() ? carNumber : car.carNumber;
        carColor = car.carColor.isEmpty() ? carColor : car.carColor;
        carMark = car.carMark.isEmpty() ? carMark : car.carMark;
        isForeign = car.isForeign == -1 ? isForeign : car.isForeign;
    }

    @Override
    public String toString() {
        return carMark + ", " + carNumber;
    }
}
