package ie.demo.cotroller.impl;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.reactive.server.WebTestClient;

import ie.demo.api.model.IrisDTO;
import ie.demo.cotroller.IrisController;
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TestInstance( Lifecycle.PER_CLASS )
public class IrisControllerTest {

	WebTestClient webTestClient;
	IrisController irisController;
	IrisService irisService;

	static PageRequest defaultPagig = PageRequest.of( 0, 10 );

	@BeforeAll
	public void setUp() throws Exception {
		irisService = Mockito.mock( IrisService.class );
		irisController = new IrisControllerImpl( irisService );
		webTestClient = WebTestClient.bindToController( irisController ).build();
	}

	@Test
	public void getIrisById() {
		BDDMockito.given( irisService.getIrisById( "someId" ) )
			.willReturn( 
				Mono.just( 
					IrisDTO.builder()
						.petalLength( new BigDecimal( "1.4" ) )
						.petalWidth( new BigDecimal( "0.2" ) )
						.sepalLength( new BigDecimal( "5.1" ) )
						.sepalWidth( new BigDecimal( "3.5" ) )
						.species( "setosa" )
					.build() 
				) 
			);

		webTestClient.get()
			.uri( "/irises/someId" )
			.exchange()
			.expectBody( IrisDTO.class );
	}

	@Test
	public void getAllIrises() {
		BDDMockito.given( irisService.getAllIris( defaultPagig ) )
			.willReturn(
				Flux.just( 
					IrisDTO.builder()
						.petalLength( new BigDecimal( "1.4" ) )
						.petalWidth( new BigDecimal( "0.2" ) )
						.sepalLength( new BigDecimal( "5.1" ) )
						.sepalWidth( new BigDecimal( "3.5" ) )
						.species( "setosa" )
					.build()
				)
			);

		webTestClient.get()
			.uri( "/irises" )
			.exchange()
			.expectBodyList( IrisDTO.class )
			.hasSize( 1 );
	}

	@Test
	public void getAllSpecies() {
		BDDMockito.given( irisService.getAllSpecies() )
			.willReturn( Flux.just( 
					IrisDTO.builder().species( "setosa" ).build(),
					IrisDTO.builder().species( "versicolor" ).build(),
					IrisDTO.builder().species( "virginica" ).build() ) );

		webTestClient.get()
			.uri( "/irises/species" )
			.exchange()
			.expectBodyList( IrisDTO.class )
			.hasSize( 3 );
	}

	@Test
	public void getIrisBySpecies() {
		String species = "setosa";
		BDDMockito.given( irisService.getIrisBySpecies( species, defaultPagig ) )
			.willReturn(
				Flux.just( 
					IrisDTO.builder()
						.petalLength( new BigDecimal( "1.4" ) )
						.petalWidth( new BigDecimal( "0.2" ) )
						.sepalLength( new BigDecimal( "5.1" ) )
						.sepalWidth( new BigDecimal( "3.5" ) )
						.species( species )
						.build()
					)
			);

		webTestClient.get()
			.uri( "/irises/species/" + species )
			.exchange()
			.expectBodyList( IrisDTO.class )
			.hasSize( 1 );
	}

	@Test
	public void saveIris() {
		Flux<IrisDTO> irisToSave =
			Flux.just( 
				IrisDTO.builder()
					.petalLength( new BigDecimal( "1.4" ) )
					.petalWidth( new BigDecimal( "0.2" ) )
					.sepalLength( new BigDecimal( "5.1" ) )
					.sepalWidth( new BigDecimal( "3.5" ) )
					.species( "setosa" )
				.build() );

		BDDMockito.given( irisService.saveIris( irisToSave ) ).willReturn( irisToSave );

		webTestClient.post()
			.uri( "/irises" )
			.body( irisToSave, IrisDTO.class )
			.exchange()
			.expectStatus()
			.isCreated();
	}

	@Test
	public void deleteIris() {
		String id = "randomid";
		BDDMockito.given( irisService.deleteIris( id ) )
			.willReturn( Mono.empty() );

		webTestClient.delete()
			.uri( "/irises/" + id )
			.exchange()
			.expectStatus()
			.isOk();
	}
}