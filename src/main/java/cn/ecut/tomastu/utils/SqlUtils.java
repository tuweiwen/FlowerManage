package cn.ecut.tomastu.utils;

import cn.ecut.tomastu.bean.Flower;
import cn.ecut.tomastu.bean.Orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class SqlUtils {
    // todo(change statement to preparedStatement)
    public static Statement getStatement() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://sh-cynosdbmysql-grp-9l5cq7ps.sql.tencentcdb.com" +
                ":23152/flowerManager?servertime=UTC", "root", "2001Z03z07f?@!");
        conn.setAutoCommit(false);

//        conn.commit();
//        smt.close();
//        conn.close();

        return conn.createStatement();

    }

    // table user
    public static boolean login(String username, String password) {
        try {
            Statement s = getStatement();
//            Connection c = s.getConnection();
            ResultSet resultSet = s.executeQuery("select * from user where username = '" + username + "'");
            String saltedMD5 = "";
            while (resultSet.next()) {
                saltedMD5 = resultSet.getString("password");
            }
            s.close();
//            c.close();
            return PasswordUtils.getSaltverifyMD5(password, saltedMD5);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // table flower
    public static LinkedList<Flower> getFlowerAll() {
        try {
            Statement s = getStatement();
//            Connection c = s.getConnection();
            ResultSet resultSet = s.executeQuery("select * from flower");
//            s.close();
//            c.close();
            return getFlowers(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public static LinkedList<Flower> getFlowerByName(String name) {
        try {
            Statement s = getStatement();
//            Connection c = s.getConnection();
            ResultSet resultSet = s.executeQuery("select * from flower where name like '%" + name + "%'");
//            s.close();
//            c.close();
            return getFlowers(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private static LinkedList<Flower> getFlowers(ResultSet resultSet) {
        LinkedList<Flower> results = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Flower result = new Flower();
                result.setName(resultSet.getString("name"));
                result.setStorage(resultSet.getLong("storage"));
                result.setPrice(resultSet.getDouble("price"));
                results.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static long queryFlowerStorage(String name) {
        int result = 0;
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();
//            System.out.println("select count(*) from flower where name = '" + name + "'");
            ResultSet r = s.executeQuery("select storage from flower where name = '" + name + "'");
            while(r.next()) {
                result = r.getInt("storage");
            }
            c.commit();
            s.close();
//            c.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean queryFlowerUnique(String name) {
        int result = 0;
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();
//            System.out.println("select count(*) from flower where name = '" + name + "'");
            ResultSet r = s.executeQuery("select count(*) from flower where name = '" + name + "'");
            while(r.next()) {
                result = r.getInt(1);
            }
            c.commit();
            s.close();
//            c.close();
            return result == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateFlowerStorage(String name, long orders) {
        int count = 0;
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();;
            count = s.executeUpdate("update flower set storage = storage - " + orders + " where name = '" + name + "'");
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    public static boolean updateFlowerAll(String oldName, String newName, double price, double storage) {
        int count = 0;
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();;
            count = s.executeUpdate("update flower set name = '" + newName + "', price = " + price + ", storage = " + storage + " where name = '" + oldName + "'");
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    public static void addFlower(String newName, double newPrice, long newStorage) {
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();
            s.execute("insert into flower value ('" + newName + "', " + newPrice + ", " + newStorage + ")");
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFlowerByName(String name) {
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();
//            System.out.println(s.executeUpdate("delete from flower where name = '" + name + "'"));
            s.executeUpdate("delete from flower where name = '" + name + "'");
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // table orders
    public static LinkedList<Orders> getOrdersAll() {
        try {
            Statement s = getStatement();
//            Connection c = s.getConnection();
            ResultSet resultSet = s.executeQuery("select * from orders");
//            s.close();
//            c.close();
            return getOrders(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private static LinkedList<Orders> getOrders(ResultSet resultSet) {
        LinkedList<Orders> results = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Orders result = new Orders();
                result.setAmount(resultSet.getLong("amount"));
                result.setDiscount(resultSet.getDouble("discount"));
                result.setOrdersId(resultSet.getLong("orders_id"));
                result.setOperator(resultSet.getString("operator"));
                result.setOrdersDate(resultSet.getDate("orders_date"));
                result.setFlowerName(resultSet.getString("flower_name"));
                results.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static LinkedList<Orders> getOrdersById(String ordersId) {
        try {
            Statement s = getStatement();
//            Connection c = s.getConnection();
            ResultSet resultSet = s.executeQuery("select * from orders where orders_id = " + ordersId);
//            s.close();
//            c.close();
            return getOrders(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public static void deleteOrdersById(Long ordersId) {
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();
//            System.out.println(s.executeUpdate("delete from flower where name = '" + name + "'"));
            s.executeUpdate("delete from orders where orders_id = " + ordersId);
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addOrders(String flowerName, long amount, double discount, String operator) {
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();
            s.execute("insert into orders(flower_name, amount, discount, operator) value ('" + flowerName + "', " + amount + ", " + discount + ", '" + operator + "')");
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean updateOrdersAll(Long ordersId, String flowerName, long amount, double discount) {
        int count = 0;
        try {
            Statement s = getStatement();
            Connection c = s.getConnection();;
            count = s.executeUpdate("update orders set flower_name = '" + flowerName + "', amount = " + amount + ", discount = " + discount + " where orders_id = '" + ordersId + "'");
            c.commit();
            s.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count != 0;
    }
}
