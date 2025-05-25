package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long beforePrice;

    private long afterPrice;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @ManyToMany(mappedBy = "subscribers")
    private List<Post> subscribedPosts = new ArrayList<>();

}