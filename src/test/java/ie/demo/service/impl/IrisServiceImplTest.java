package ie.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import ie.demo.api.mapper.IrisMapper;
import ie.demo.api.model.IrisDTO;
import ie.demo.domain.Iris;
import ie.demo.repository.IrisRepository;
import ie.demo.service.IrisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@DataMongoTest
public class IrisServiceImplTest {

	IrisMapper irisMapper;
	IrisRepository irisRepository;

	IrisService irisService;

	static final String ID = "someid";
	static final String SPECIES = "randomspecies";

	static final String ID2 = "someid";
	static final String SPECIES2 = "randomspecies";

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

	@Before
	public void setUp() throws Exception {
		irisMapper = Mockito.mock( IrisMapper.class );
		irisRepository = Mockito.mock( IrisRepository.class );
		irisService = new IrisServiceImpl( irisMapper, irisRepository );
	}

	@Test
	public void getIrisById() {

		when( irisRepository.findById( ID ) ).thenReturn(  Mono.just( IRIS ) );
		when( irisMapper.toIrisDTO( any() ) ).thenReturn( IRISDTO );

		IrisDTO result = irisService.getIrisById( ID ).block();

		assertEquals( result.getId(), ID );
		assertTrue( result.equals( IRISDTO ) );
	}

	@Test
	public void getAllIris() {

		when( irisRepository.findAll() ).thenReturn( Flux.just( IRIS, IRIS2 ) );
		when( irisMapper.toIrisDTO( IRIS ) ).thenReturn( IRISDTO );
		when( irisMapper.toIrisDTO( IRIS2 ) ).thenReturn( IRISDTO2 );

		Flux<IrisDTO>result = irisService.getAllIris();

		assertEquals( result.blockFirst().getId(), ID );
		assertEquals( result.blockLast().getId(), ID2 );
		assertEquals( result.count().block().toString(), "2" );
		assertTrue( result.blockFirst().equals( IRISDTO ) );
		assertTrue( result.blockLast().equals( IRISDTO2 ) );
	}

	@Test
	public void getIrisBySpecies() {

		when( irisRepository.findBySpecies( SPECIES ) ).thenReturn( Flux.just( IRIS ) );
		when( irisRepository.findBySpecies( SPECIES2 ) ).thenReturn( Flux.just( IRIS2 ) );
		when( irisMapper.toIrisDTO( IRIS ) ).thenReturn( IRISDTO );
		when( irisMapper.toIrisDTO( IRIS2 ) ).thenReturn( IRISDTO2 );

		Flux<IrisDTO>result = irisService.getIrisBySpecies( SPECIES );

		assertEquals( result.blockFirst().getId(), ID );
		assertEquals( result.count().block().toString(), "1" );

		Flux<IrisDTO>result2 = irisService.getIrisBySpecies( SPECIES2 );

		assertEquals( result2.blockFirst().getId(), ID2 );
		assertEquals( result2.count().block().toString(), "1" );

		verify( irisMapper, times( 4 ) ).toIrisDTO( any() );
		verify( irisRepository, times( 2 ) ).findBySpecies( any() );
	}
}