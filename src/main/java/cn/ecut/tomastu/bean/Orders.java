package cn.ecut.tomastu.bean;

import java.util.Date;
import java.util.Objects;

public class Orders {
    private Long ordersId;
    private Date ordersDate;
    private String flowerName;
    private Long amount;
    private Double discount;
    private String operator;

    @Override
    public String toString() {
        return "Orders{" +
                "ordersId=" + ordersId +
                ", ordersDate=" + ordersDate +
                ", flowerName='" + flowerName + '\'' +
                ", amount=" + amount +
                ", discount=" + discount +
                ", operator='" + operator + '\'' +
                '}';
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Date getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(Date ordersDate) {
        this.ordersDate = ordersDate;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return ordersId.equals(orders.ordersId) && ordersDate.equals(orders.ordersDate) && flowerName.equals(orders.flowerName) && amount.equals(orders.amount) && Objects.equals(discount, orders.discount) && operator.equals(orders.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordersId, ordersDate, flowerName, amount, discount, operator);
    }
}
