package ie.demo.service.impl;

import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ie.demo.api.mapper.IrisMapper;
import ie.demo.api.mapper.IrisSpeciesMapper;
import ie.demo.api.model.IrisDTO;
import ie.demo.repository.IrisRepository;
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IrisServiceImpl implements IrisService {

	final IrisMapper irisMapper;
	final IrisRepository irisRepository;
	final ReactiveMongoTemplate reactiveMongoTemplate;
	final IrisSpeciesMapper irisSpeciesMapper;

	public IrisServiceImpl( IrisMapper irisMapper, IrisRepository irisRepository,
			ReactiveMongoTemplate reactiveMongoTemplate, IrisSpeciesMapper irisSpeciesMapper ) {
		this.irisMapper = irisMapper;
		this.irisRepository = irisRepository;
		this.reactiveMongoTemplate = reactiveMongoTemplate;
		this.irisSpeciesMapper = irisSpeciesMapper;
	}

	/*
	 * @see ie.demo.service.IrisService#getIrisById(org.reactivestreams.Publisher)
	 */
	@Override
	public Mono<IrisDTO> getIrisById( String id ) {
		return irisRepository.findById( id ).map( irisMapper :: toIrisDTO );
	}

	/*
	 * @see ie.demo.service.IrisService#getAllIris()
	 */
	@Override
	public Flux<IrisDTO> getAllIris() {
		return irisRepository.findAll().map( irisMapper :: toIrisDTO );
	}

	/*
	 * @see ie.demo.service.IrisService#getAllSpecies()
	 */
	@Override
	public Flux<IrisDTO> getAllSpecies() {
		return reactiveMongoTemplate.findDistinct( new Query(), "species", "iris", String.class ).map( irisSpeciesMapper :: toIrisDTO );
	}
	
	/*
	 * @see ie.demo.service.IrisService#getIrisBySpecies(java.lang.String)
	 */
	public Flux<IrisDTO> getIrisBySpecies( String species ) {
		return irisRepository.findBySpecies( species ).map( irisMapper :: toIrisDTO );
	}

	/*
	 * @see ie.demo.service.IrisService#saveIris(org.reactivestreams.Publisher)
	 */
	@Override
	public Flux<IrisDTO> saveIris( Publisher<IrisDTO> irises ) {
		return Flux.from( irises )
			.map( irisMapper :: toIris )
			.publish( irisRepository:: saveAll )
			.map( irisMapper :: toIrisDTO );
	}

	/*
	 * @see ie.demo.service.IrisService#deleteIris(org.reactivestreams.Publisher)
	 */
	@Override
	public Mono<Void> deleteIris( String id ) {
		return irisRepository.deleteById( id );
	}
}