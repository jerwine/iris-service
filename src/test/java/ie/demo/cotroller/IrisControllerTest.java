package ie.demo.cotroller;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import ie.demo.api.model.IrisDTO;
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IrisControllerTest {

	WebTestClient webTestClient;
	IrisController irisController;
	IrisService irisService;

	@Before
	public void setUp() throws Exception {
		irisService = Mockito.mock( IrisService.class );
		irisController = new IrisController( irisService );
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
		BDDMockito.given( irisService.getAllIris() )
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
		BDDMockito.given( irisService.getAllSpecies())
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
		BDDMockito.given( irisService.getIrisBySpecies( "setosa" ) )
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
			.uri( "/irises/species/setosa" )
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