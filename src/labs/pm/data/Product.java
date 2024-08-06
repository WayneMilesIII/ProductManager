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
package labs.pm.data;


import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import java.util.Objects;


/**
 * {@code Product} Class represents properties and behaviors of 
 * product objects in the Product Management System. 
 * @author Wayne Miles 
 */
public abstract class Product implements Rateable<Product> {
    private final int id;
    private final String name;
    private final Rating rating;
    
    /**
     * A constant that defines a 
     * {@link java.math.BigDecimal BigDecimal} value of the discount rate
     * <br>
     * Discount rate is 10%
     */
    private BigDecimal price;
    public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);

    Product(int id, String name, BigDecimal price, Rating rating) { //Why does the order matter here?
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    /*
    * {@link DISCOUNT_RATE discount rate} 
    * A method that returns the discounted price of a product
    * @return a {@link java.math.BigDecimal BigDecimal}
    */
    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    public static BigDecimal getDISCOUNT_RATE() {
        return DISCOUNT_RATE;
    }

    @Override
    public Rating getRating() {
        return rating;
    }
    

    /**
     * Assumes that the best before date is today
     * 
     * @return the current date
     */

    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: " + price + ", Discount: " + getDiscount() + ", Rating: " + rating.getStars() + ", Best Before: " + getBestBefore();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        // How does this compare?
        if (obj instanceof Product) {
            final Product other = (Product)obj;
            return this.id == other.id && Objects.equals(this.name, other.name);
        }
        return false;
    }
    
   
}
