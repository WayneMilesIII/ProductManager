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
import java.time.LocalDate;

/**
 *
 * @author theli
 */
public final class Food extends Product {
    private final LocalDate bestBefore;
    
    /**
     * Get the value of bestBefore
     * @return the value of bestBefore
     */

    @Override
    public LocalDate getBestBefore() {
        return bestBefore;
    }

    @Override
    public BigDecimal getDiscount() {
        return (bestBefore.isEqual(LocalDate.now())) ? super.getDiscount() : BigDecimal.ZERO;
    }
    
    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore){
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    } 
    
    @Override
    public Product applyRating(Rating newRating){
        return new Food(getId(), getName(), getPrice(), newRating, bestBefore);
    }
    
    
}
