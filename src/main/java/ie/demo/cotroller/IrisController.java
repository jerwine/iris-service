package ie.demo.cotroller;

import org.reactivestreams.Publisher;

import ie.demo.api.model.IrisDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IrisController {

	/**
	 * Retrieve all the Irises, by default the first 10
	 * @param page - optional
	 * @param size - optional
	 * @return
	 */
	public Flux<IrisDTO> getAllIrises( int page, int size );

	/**
	 * Retrieve an Iris identified by the ID
	 * @param id
	 * @return
	 */
	public Mono<IrisDTO> getIris( String id );

	/**
	 * Get all the species
	 * @return
	 */
	public Flux<IrisDTO> getAllSpecies();

	/**
	 * Get all irises by the species, by default the first 10
	 * @param species
	 * @param page
	 * @param size
	 * @return
	 */
	public Flux<IrisDTO> getIrisBySpecies( String species,  int page, int size );

	/**
	 * Create/Update an Iris
	 * @param irisStream
	 * @return
	 */
	public Flux<IrisDTO> saveOrUpdateIris( Publisher<IrisDTO> irisStream );

	/**
	 * Delete an Iris identified by the ID
	 * @param id
	 * @return
	 */
	public Mono<Void> deleteIris( String id );
}