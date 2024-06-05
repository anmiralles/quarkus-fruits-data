package me.amiralles.fruits;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Cacheable
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

    @Column
    public Long itemId;

    public Fruit() {
    }

    public Fruit(String name, Long itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public static Uni<Fruit> findByName(String name){
        return find("name", name).firstResult();
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", itemId=" + itemId +
                ", id=" + id +
                '}';
    }
}
