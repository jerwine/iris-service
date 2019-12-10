package ie.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ie.demo.domain.Iris;
import reactor.core.publisher.Flux;

public interface IrisRepository extends ReactiveMongoRepository<Iris, String> {

	/**
	 * Find Irises by the species
	 * @param species
	 * @return
	 */
	Flux<Iris> findBySpecies( String species );

	/**
	 * Retrieve all the Irises restricted by the pageable parameter
	 * @param pageable
	 * @return
	 */
	@Query("{ id: { $exists: true } }")
	Flux<Iris> retrieveAllPageable( final Pageable pageable );

	/**
	 * Retrieve all the Irises identified with the species restricted by the pageable parameter
	 * @param species
	 * @param pageable
	 * @return
	 */
	@Query("{ id: { $exists: true } }")
	Flux<Iris> retrieveBySpeciesPageable( final String species, final Pageable pageable );
}