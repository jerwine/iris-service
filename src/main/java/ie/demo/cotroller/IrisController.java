package ie.demo.cotroller;

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
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/irises")
public class IrisController {

	final IrisService irisService;

	public IrisController( IrisService irisService ) {
		this.irisService = irisService;
	}

	@GetMapping()
	@ResponseStatus( HttpStatus.OK )
	public Flux<IrisDTO> getAllIrises( @RequestParam( name = "page", defaultValue = "0", required = false ) int page,
			@RequestParam( name = "size", defaultValue = "10", required = false ) int size ) {
		return irisService.getAllIris( PageRequest.of( page, size ) );
	}

	@GetMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	public Mono<IrisDTO> getIris( @PathVariable String id ) {
		return irisService.getIrisById( id );
	}

	@GetMapping("/species")
	@ResponseStatus( HttpStatus.OK )
	public Flux<IrisDTO> getAllSpecies() {
		return irisService.getAllSpecies();
	}

	@GetMapping("/species/{species}")
	@ResponseStatus( HttpStatus.OK )
	public Flux<IrisDTO> getIrisBySpecies( @PathVariable String species,
		@RequestParam( name = "page", defaultValue = "0", required = false ) int page,
		@RequestParam( name = "size", defaultValue = "10", required = false ) int size) {
		return irisService.getIrisBySpecies( species, PageRequest.of( page, size ) );
	}

	@PostMapping()
	@ResponseStatus( HttpStatus.CREATED )
	public Flux<IrisDTO> saveOrUpdateIris( @RequestBody Publisher<IrisDTO> irisStream ) {
		return irisService.saveIris( irisStream );
	}

	@DeleteMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	public Mono<Void> deleteIris( @PathVariable String id ) {
		return irisService.deleteIris( id );
	}
}