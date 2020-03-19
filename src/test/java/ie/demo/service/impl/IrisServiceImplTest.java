package ie.demo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import ie.demo.api.mapper.IrisMapper;
import ie.demo.api.mapper.IrisSpeciesMapper;
import ie.demo.api.model.IrisDTO;
import ie.demo.domain.Iris;
import ie.demo.repository.IrisRepository;
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DataMongoTest
@TestInstance( Lifecycle.PER_CLASS )
public class IrisServiceImplTest {

	IrisMapper irisMapper;
	IrisRepository irisRepository;
	ReactiveMongoTemplate reactiveMongoTemplate;
	IrisSpeciesMapper irisSpeciesMapper;

	IrisService irisService;

	static final String ID = "someid";
	static final String SPECIES = "randomspecies";

	static final String ID2 = "someid";
	static final String SPECIES2 = "anotherrandomspecies";
	static final String SPECIES3 = "onemorerandomspecies";

	static final Iris IRIS = Iris.builder()
			.id( ID )
			.species( SPECIES )
			.petalLength( BigDecimal.ONE )
			.petalWidth( BigDecimal.ZERO )
			.sepalLength( BigDecimal.ONE )
			.sepalWidth( BigDecimal.ZERO )
		.build();

	static final Iris IRIS2 = Iris.builder()
			.id( ID2 )
			.species( SPECIES2 )
			.petalLength( BigDecimal.ZERO )
			.petalWidth( BigDecimal.ONE )
			.sepalLength( BigDecimal.ZERO )
			.sepalWidth( BigDecimal.ONE )
		.build();

	static final IrisDTO IRISDTO = IrisDTO.builder()
			.id( ID )
			.species( SPECIES )
			.petalLength( BigDecimal.ONE )
			.petalWidth( BigDecimal.ZERO )
			.sepalLength( BigDecimal.ONE )
			.sepalWidth( BigDecimal.ZERO )
		.build();

	static final IrisDTO IRISDTO2 = IrisDTO.builder()
			.id( ID2 )
			.species( SPECIES2 )
			.petalLength( BigDecimal.ZERO )
			.petalWidth( BigDecimal.ONE )
			.sepalLength( BigDecimal.ZERO )
			.sepalWidth( BigDecimal.ONE )
		.build();

	static final IrisDTO SPECIES_1 = IrisDTO.builder().species( SPECIES ).build();
	static final IrisDTO SPECIES_2 = IrisDTO.builder().species( SPECIES2 ).build();
	static final IrisDTO SPECIES_3 = IrisDTO.builder().species( SPECIES3 ).build();

	static final PageRequest defaultPagig = PageRequest.of( 1, 10 );

	@BeforeAll
	public void setUp() throws Exception {
		irisMapper = Mockito.mock( IrisMapper.class );
		irisRepository = Mockito.mock( IrisRepository.class );
		reactiveMongoTemplate = Mockito.mock( ReactiveMongoTemplate.class );
		irisSpeciesMapper = Mockito.mock( IrisSpeciesMapper.class );
		irisService = new IrisServiceImpl( irisMapper, irisRepository, reactiveMongoTemplate, irisSpeciesMapper );
	}

	@Test
	public void getIrisById() {

		when( irisRepository.findById( ID ) ).thenReturn(  Mono.just( IRIS ) );
		when( irisMapper.toIrisDTO( IRIS ) ).thenReturn( IRISDTO );

		IrisDTO result = irisService.getIrisById( ID ).block();

		assertThat( result.getId() ).isEqualTo( ID );
		assertThat( result.equals( IRISDTO ) ).isTrue();
	}

	@Test
	public void getAllIris() {

		when( irisRepository.retrieveAllPageable( defaultPagig ) ).thenReturn( Flux.just( IRIS, IRIS2 ) );
		when( irisMapper.toIrisDTO( IRIS ) ).thenReturn( IRISDTO );
		when( irisMapper.toIrisDTO( IRIS2 ) ).thenReturn( IRISDTO2 );

		Flux<IrisDTO> result = irisService.getAllIris( defaultPagig );

		assertThat( result.blockFirst().getId() ).isEqualTo( ID );
		assertThat( result.blockLast().getId() ).isEqualTo( ID2 );
		assertThat( result.count().block().toString() ).isEqualTo( "2" );
		assertThat( result.blockFirst() ).isEqualTo( IRISDTO );
		assertThat( result.blockLast() ).isEqualTo( IRISDTO2 );
	}

	@Test
	public void getAllSpecies() {

		when( reactiveMongoTemplate.findDistinct( new Query(), "species", "iris", String.class ) ).thenReturn( Flux.just( SPECIES, SPECIES2, SPECIES3 ) );
		when( irisSpeciesMapper.toIrisDTO( SPECIES ) ).thenReturn( SPECIES_1 );
		when( irisSpeciesMapper.toIrisDTO( SPECIES2 ) ).thenReturn( SPECIES_2 );
		when( irisSpeciesMapper.toIrisDTO( SPECIES3 ) ).thenReturn( SPECIES_3 );

		Flux<IrisDTO>result = irisService.getAllSpecies();

		assertThat( result.blockFirst().getSpecies() ).isEqualTo( SPECIES );
		assertThat( result.blockLast().getSpecies() ).isEqualTo( SPECIES3 );
		assertThat( result.count().block().toString() ).isEqualTo( "3" );
		assertThat( result.blockFirst() ).isEqualTo( SPECIES_1 );
		assertThat( result.blockLast() ).isEqualTo( SPECIES_3 );
	}

	@Test
	public void getIrisBySpecies() {

		when( irisRepository.retrieveBySpeciesPageable( SPECIES, defaultPagig ) ).thenReturn( Flux.just( IRIS, IRIS2 ) );
		when( irisMapper.toIrisDTO( IRIS ) ).thenReturn( IRISDTO );
		when( irisMapper.toIrisDTO( IRIS2 ) ).thenReturn( IRISDTO2 );

		Flux<IrisDTO>result = irisService.getIrisBySpecies( SPECIES, defaultPagig );

		assertThat( result.blockFirst().getId() ).isEqualTo( ID );
		assertThat( result.count().block().toString() ).isEqualTo( "2" );
		verify( irisRepository, times( 1 ) ).retrieveBySpeciesPageable( any(), any() );
	}

	@Test
	public void testDeleteIris() {
		irisService.deleteIris( "random" );
		verify( irisRepository, times( 1 ) ).deleteById( "random" );
	}
}