package models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_product")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 250, nullable = false)
    private String name;

    @Column(length = 4000, nullable = false)
    private String description;

    @Column
    private float price;

    public Product(String name, String description, float price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    public String toString() {
        return String.format("Id: %s | Name: %s | Desctiption: %s | Price: %s | Category: %s", this.id, this.name, this.description, this.price, this.category.getName());
    }
}
