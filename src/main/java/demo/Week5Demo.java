package demo;

import catalog.Product;
import domain.LineItem;
import domain.Order;
import factory.ProductFactory;

public final class Week5Demo {
    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();
        Product p1 = factory.create("ESP+SHOT+OAT");
        Product p2 = factory.create("LAT+L");
        Order order = new Order(OrderIds.next());
        order.addItem(new LineItem(p1, 1));
        order.addItem(new LineItem(p2, 2));
        System.out.println("Order #" + order.getId());
        for (LineItem li : order.getItems()) {
            System.out.println(" - " + li.product().name() + " x" + li.quantity() + " = " + li.lineTotal());
        }

        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " + order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    }

    private static class OrderIds{
        private static long id = 1001;

        public static long next(){
            return id++;
        }
    }
}
