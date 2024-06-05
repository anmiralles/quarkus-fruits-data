package me.amiralles.fruits;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import me.amiralles.fruits.schema.FruitCreated;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
public class FruitService {

    @Channel("fruits")
    MutinyEmitter<FruitCreated> emitter;

    @Inject
    FruitMapper fruitMapper;

    @WithSpan("FruitService.create")
    public Uni<Response> create(Fruit fruit) {
        return Panache.withTransaction(fruit::persist)
                .replaceWith(fruit)
                .map(this.fruitMapper::toSchema)
                .invoke(fruitCreated -> this.emitter.sendMessageAndForget(Message.of(fruitCreated)))
                .replaceWith(Response.ok(fruit).status(CREATED)::build);
    }

}
