package ie.demo.cotroller.impl;

import org.reactivestreams.Publisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ie.demo.api.model.IrisDTO;
import ie.demo.cotroller.IrisController;
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/irises")
public class IrisControllerImpl implements IrisController {

	final IrisService irisService;

	public IrisControllerImpl( IrisService irisService ) {
		this.irisService = irisService;
	}

	/*
	 * @see ie.demo.cotroller.IrisController#getAllIrises(int, int)
	 */
	@GetMapping()
	@ResponseStatus( HttpStatus.OK )
	@Override
	public Flux<IrisDTO> getAllIrises( @RequestParam( name = "page", defaultValue = "0", required = false ) int page,
			@RequestParam( name = "size", defaultValue = "10", required = false ) int size ) {
		return irisService.getAllIris( PageRequest.of( page, size ) );
	}

	/*
	 * @see ie.demo.cotroller.IrisController#getIris(java.lang.String)
	 */
	@GetMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	@Override
	public Mono<IrisDTO> getIris( @PathVariable String id ) {
		return irisService.getIrisById( id );
	}

	/*
	 * @see ie.demo.cotroller.IrisController#getAllSpecies()
	 */
	@GetMapping("/species")
	@ResponseStatus( HttpStatus.OK )
	@Override
	public Flux<IrisDTO> getAllSpecies() {
		return irisService.getAllSpecies();
	}

	/*
	 * @see ie.demo.cotroller.IrisController#getIrisBySpecies(java.lang.String, int, int)
	 */
	@GetMapping("/species/{species}")
	@ResponseStatus( HttpStatus.OK )
	@Override
	public Flux<IrisDTO> getIrisBySpecies( @PathVariable String species,
		@RequestParam( name = "page", defaultValue = "0", required = false ) int page,
		@RequestParam( name = "size", defaultValue = "10", required = false ) int size ) {
		return irisService.getIrisBySpecies( species, PageRequest.of( page, size ) );
	}

	/*
	 * @see ie.demo.cotroller.IrisController#saveOrUpdateIris(org.reactivestreams.Publisher)
	 */
	@PostMapping()
	@ResponseStatus( HttpStatus.CREATED )
	@Override
	public Flux<IrisDTO> saveOrUpdateIris( @RequestBody Publisher<IrisDTO> irisStream ) {
		return irisService.saveIris( irisStream );
	}

	/*
	 * @see ie.demo.cotroller.IrisController#deleteIris(java.lang.String)
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	@Override
	public Mono<Void> deleteIris( @PathVariable String id ) {
		return irisService.deleteIris( id );
	}
}