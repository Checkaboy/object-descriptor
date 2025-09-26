package com.checkaboy.descriptor.test;

import com.checkaboy.descriptor.FieldDescriptor;
import com.checkaboy.descriptor.ObjectDescriptor;
import com.checkaboy.descriptor.model.Car;
import com.checkaboy.descriptor.model.Engine;
import com.checkaboy.descriptor.model.Transmission;
import com.checkaboy.descriptor.typifier.EFieldType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Taras Shaptala
 */
public class FieldDescriptorTest {

    @Test
    public void simpleTest() {
        FieldDescriptor<Car, String> descriptor = new FieldDescriptor<>(
                EFieldType.PRIMITIVE, String.class, "color", true, Car::getColor, Car::setColor
        );
        Car car = new Car();
        final String color = "test color";
        descriptor.set(car, color);
        Assert.assertEquals(color, descriptor.get(car));
    }

    @Test
    public void simpleTest2() {
        FieldDescriptor<Car, String> descriptor = new FieldDescriptor<>(
                EFieldType.PRIMITIVE, String.class, "color", true, Car::getColor, Car::setColor
        );
        Car car = new Car();
        final String color = "test color";
        descriptor.set(car, color);
        Assert.assertEquals(color, descriptor.get(car));
    }

    @Test
    public void objectTest() {
        ObjectDescriptor<Car> descriptor = new ObjectDescriptor<>();

        descriptor.put(new FieldDescriptor<>(EFieldType.PRIMITIVE, int.class, "doorCount", true, Car::getDoorCount, Car::setDoorCount));
        descriptor.put(new FieldDescriptor<>(EFieldType.PRIMITIVE, String.class, "color", true, Car::getColor, Car::setColor));
        descriptor.put(new FieldDescriptor<>(EFieldType.PRIMITIVE, String.class, "carBrand", true, Car::getCarBrand, Car::setCarBrand));
        descriptor.put(new FieldDescriptor<>(EFieldType.PRIMITIVE, String.class, "model", true, Car::getModel, Car::setModel));
        descriptor.put(new FieldDescriptor<>(EFieldType.OBJECT, Engine.class, "engine", true, Car::getEngine, Car::setEngine));
        descriptor.put(new FieldDescriptor<>(EFieldType.OBJECT, Transmission.class, "transmission", true, Car::getTransmission, Car::setTransmission));

        Car car = new Car();
        car.setDoorCount(3);
        car.setColor("color");
        car.setCarBrand("carBrand");
        car.setModel("model");

        System.out.println(descriptor.valueGet("doorCount", car).toString());
    }

}
