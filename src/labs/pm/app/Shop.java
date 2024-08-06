/*
 * Copyright (C) 2024 theli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * {@code Shop} Class represents an application that manages Products
 * @version 4.0
 * @author Wayne Miles 
 */
package labs.pm.app;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import labs.pm.data.Rating;
import labs.pm.data.Product;
import labs.pm.data.ProductManager;

public class Shop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ProductManager pm = new ProductManager(Locale.UK);
        Product p1 = pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.printProductReport();
        p1 = pm.reviewProduct(p1, Rating.FOUR_STAR, "Nice hot cup of tea!");
        p1 = pm.reviewProduct(p1, Rating.TWO_STAR, "Rather weak tea");
        p1 = pm.reviewProduct(p1, Rating.FOUR_STAR, "Rather weak tea");
        p1 = pm.reviewProduct(p1, Rating.FOUR_STAR, "Rather weak tea");
        p1 = pm.reviewProduct(p1, Rating.FIVE_STAR, "Rather weak tea");
        p1 = pm.reviewProduct(p1, Rating.THREE_STAR, "Just add some lemon!");
        pm.printProductReport();

       
    }
    
}
