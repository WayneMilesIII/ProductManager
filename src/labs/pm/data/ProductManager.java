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
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author theli
 */

public class ProductManager {
    
    private static class ResourceFormatter {
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormat;
        private NumberFormat moneyFormat;
    
        private ResourceFormatter(Locale locale){
            this.locale = locale;
            resources = ResourceBundle.getBundle("labs.pm.data.resources", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
    }
    
    private String formatProduct(Product product){
        return MessageFormat.format(resources.getString("product"), 
                    product.getName(), moneyFormat.format(product.getPrice()), 
                    product.getRating().getStars(), dateFormat.format(product.getBestBefore()));
    }
    
    private String formatReview(Review review){
            return MessageFormat.format(resources.getString("review"), review.getRating().getStars(), review.getComments());
    }
    
    private String getText(String key){
        return resources.getString(key);
    }
}
    private ResourceFormatter formatter;
    private Map<Product,List<Review>> products = new HashMap<>();
    private static Map<String,ResourceFormatter> formatters = Map.of("en-GB", new ResourceFormatter(Locale.UK),
                                                                      "en-US", new ResourceFormatter(Locale.US),
                                                                      "fr-FR", new ResourceFormatter(Locale.FRANCE),
                                                                      "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
                                                                      "zh-CN", new ResourceFormatter(Locale.CHINA));


    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }
    
    public ProductManager(String languageTag){
        changeLocale(languageTag);
    }
    
    public void changeLocale(String languageTag){
        formatter = formatters.getOrDefault(languageTag, formatters.get("en-US"));
    }
    
    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }
    
    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore){
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating){
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }
    
    public Product findProduct(int id){
        return products.keySet()
                       .stream()
                       .filter(p -> p.getId() == id)
                       .findFirst()
                       .orElseGet(() -> null);
    }
    
    public Product reviewProduct(int id, Rating rating, String comments){
        return reviewProduct(findProduct(id), rating, comments);
    }
    
    public Product reviewProduct(Product product, Rating rating, String comments){
        List<Review> reviews = products.get(product);
        
        product = product.applyRating(
                         Rateable.convert(
                         (int)Math.round(
                                 reviews.stream()
                                         .mapToInt(r -> r.getRating().ordinal())
                                         .average()
                                         .orElse(0))));

        products.remove(product, reviews);
        reviews.add(new Review(rating, comments));

        products.put(product, reviews);
        return product;
    }
    
     public void printProductReport(int id){
         printProductReport(findProduct(id));
     }
    
    public void printProductReport(Product product){
        List<Review> reviews = products.get(product);
        Collections.sort(reviews);
        StringBuilder txt = new StringBuilder();
        txt.append(formatter.formatProduct(product));
        txt.append('\n');
        
        if (reviews.isEmpty()){
            txt.append(formatter.getText("no.reviews")+'\n');
        }else{
            txt.append(reviews.stream()
                              .map(r->formatter.formatReview(r)+'\n')
                              .collect(Collectors.joining()));
        }
        System.out.println(txt);
    }
    
    public void printProducts(Predicate<Product> filter, 
                              Comparator<Product> sorter){
        List<Product> productList = new ArrayList<>(products.keySet());
        productList.sort(sorter);
        StringBuilder txt = new StringBuilder();
        products.keySet()
                .stream()
                .sorted(sorter)
                .filter(filter)
                .forEach(p -> txt.append(formatter.formatProduct(p)+ '\n'));
        System.out.println(txt);
    }
    
    public Map<String, String> getDiscounts(){
        return products.keySet()
                       .stream()
                       .collect(
                       Collectors.groupingBy(
                       product -> product.getRating().getStars(),
                               Collectors.collectingAndThen(
                               Collectors.summingDouble(
                                       product -> product.getDiscount().doubleValue()), 
                                       discount -> formatter.moneyFormat.format(discount))));
    }
    
    
}
