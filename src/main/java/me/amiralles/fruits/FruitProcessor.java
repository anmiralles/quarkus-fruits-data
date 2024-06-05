package me.amiralles.fruits;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import me.amiralles.fruits.schema.FruitUpdated;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class FruitProcessor {

    private static final Logger LOGGER = Logger.getLogger(FruitProcessor.class.getName());

    @Incoming("items")
    @WithTransaction
    public Uni<Response> process(FruitUpdated fruitUpdated) {

        return Fruit.findByName(fruitUpdated.getName())
                .onItem().ifNotNull().invoke(fruit -> {
                    fruit.itemId = fruitUpdated.getItemId();
                    fruit.persist();
                    LOGGER.info("Fruit processed: " + fruit);
                }).replaceWith(Response.ok(this)::build)
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }
}
