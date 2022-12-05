package cn.ecut.tomastu.bean;

import java.util.Objects;

public class Flower {
    private String name;
    private double price;
    private long storage;

    @Override
    public String toString() {
        return "flower{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", storage=" + storage +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStorage() {
        return storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return Double.compare(flower.price, price) == 0 && storage == flower.storage && name.equals(flower.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, storage);
    }
}
