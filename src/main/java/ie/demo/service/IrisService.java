package ie.demo.service;

import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;

import ie.demo.api.model.IrisDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IrisService {

	/**
	 * Retrieve an Iris identified bt the ID
	 * @param id
	 * @return
	 */
	Mono<IrisDTO> getIrisById( String id );

	/**
	 * Retrieve all the Irises restricted by the pageable parameters
	 * @param pageable
	 * @return
	 */
	Flux<IrisDTO> getAllIris( Pageable pageable );

	/**
	 * Retrieve all the distinct species of the Irises
	 * @return
	 */
	Flux<IrisDTO> getAllSpecies();

	/**
	 * Retrieve all the Irises identified by the species and restricted by the pageable parameters
	 * @param species
	 * @param pageable
	 * @return
	 */
	Flux<IrisDTO> getIrisBySpecies( String species, Pageable pageable );

	/**
	 * Persist/Update an Iris or Irises
	 * @param irises
	 * @return
	 */
	Flux<IrisDTO> saveIris( Publisher<IrisDTO> irises );

	/**
	 * Delete an Iris identified by the ID
	 * @param id
	 * @return
	 */
	Mono<Void> deleteIris( String id );
}