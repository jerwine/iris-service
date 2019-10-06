package ie.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ie.demo.domain.Iris;
import reactor.core.publisher.Flux;

public interface IrisRepository extends ReactiveMongoRepository<Iris, String> {

	Flux<Iris> findBySpecies( String species );

}