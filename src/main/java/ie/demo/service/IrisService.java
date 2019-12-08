package ie.demo.service;

import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;

import ie.demo.api.model.IrisDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IrisService {

	Mono<IrisDTO> getIrisById( String id );

	Flux<IrisDTO> getAllIris( Pageable pageable );

	Flux<IrisDTO> getAllSpecies();

	Flux<IrisDTO> getIrisBySpecies( String species, Pageable pageable );

	Flux<IrisDTO> saveIris( Publisher<IrisDTO> irises );

	Mono<Void> deleteIris( String id );
}