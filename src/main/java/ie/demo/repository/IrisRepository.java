package ie.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ie.demo.domain.Iris;
import reactor.core.publisher.Flux;

public interface IrisRepository extends ReactiveMongoRepository<Iris, String> {

	Flux<Iris> findBySpecies( String species );

	@Query("{ id: { $exists: true } }")
	Flux<Iris> retrieveAllPageable( final Pageable pageable );

	@Query("{ id: { $exists: true } }")
	Flux<Iris> retrieveBySpeciesPageable( final String species, final Pageable pageable );
}