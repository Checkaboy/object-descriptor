package com.checkaboy.descriptor.test;

import com.checkaboy.descriptor.FieldDescriptor;
import com.checkaboy.descriptor.ObjectDescriptor;
import com.checkaboy.descriptor.model.Car;
import com.checkaboy.descriptor.model.Engine;
import com.checkaboy.descriptor.model.Transmission;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Taras Shaptala
 */
public class FieldDescriptorTest {

    @Test
    public void simpleTest() {
        FieldDescriptor<Car, String> descriptor = new FieldDescriptor<>(
                String.class, "color", Car::getColor, Car::setColor
        );
        Car car = new Car();
        final String color = "test color";
        descriptor.set(car, color);
        Assert.assertEquals(color, descriptor.get(car));
    }

    @Test
    public void simpleTest2() throws NoSuchFieldException, IllegalAccessException {
        FieldDescriptor<Car, String> descriptor = new FieldDescriptor<>(Car.class, String.class, "color");
        Car car = new Car();
        final String color = "test color";
        descriptor.set(car, color);
        Assert.assertEquals(color, descriptor.get(car));
    }

    @Test
    public void objectTest() throws NoSuchFieldException, IllegalAccessException {
        ObjectDescriptor<Car> descriptor = new ObjectDescriptor<>();

        descriptor.put(new FieldDescriptor<>(Car.class, int.class, "doorCount"));
        descriptor.put(new FieldDescriptor<>(Car.class, String.class, "color"));
        descriptor.put(new FieldDescriptor<>(Car.class, String.class, "carBrand"));
        descriptor.put(new FieldDescriptor<>(Car.class, String.class, "model"));
        descriptor.put(new FieldDescriptor<>(Car.class, Engine.class, "engine"));
        descriptor.put(new FieldDescriptor<>(Car.class, Transmission.class, "transmission"));

        Car car = new Car();
        car.setDoorCount(3);
        car.setColor("color");
        car.setCarBrand("carBrand");
        car.setModel("model");

        System.out.println(descriptor.valueGet("doorCount", car).toString());
    }

}
