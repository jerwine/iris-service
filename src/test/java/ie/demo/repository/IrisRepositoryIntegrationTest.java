package ie.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;

import ie.demo.domain.Iris;

@DataMongoTest
@TestInstance( Lifecycle.PER_CLASS )
public class IrisRepositoryIntegrationTest {

	@Autowired
	IrisRepository irisRepository;

	static final Iris IRIS;

	static {
		IRIS = new Iris();
		IRIS.setId( "1" );
		IRIS.setSpecies( "setosa" );
		IRIS.setPetalLength( BigDecimal.ZERO );
		IRIS.setPetalWidth( BigDecimal.ZERO );
		IRIS.setSepalLength( BigDecimal.ZERO );
		IRIS.setSepalWidth( BigDecimal.ZERO );
	}

	@BeforeEach
	public void setUp() throws Exception {
		irisRepository.deleteAll().block();
	}

	@Test
	public void testSave() {
		assertThat( irisRepository.save( IRIS ).block() ).isEqualTo( IRIS );
	}

	@Test
	public void testSaveAll() {
		List<Iris>entities = new ArrayList<>();
		entities.add( IRIS );
		assertThat( irisRepository.saveAll( entities ).blockFirst() ).isEqualTo( IRIS );
	}

	@Test
	public void testRetrieveAllPageable() {
		irisRepository.save( IRIS ).block();
		assertThat( irisRepository.retrieveAllPageable( PageRequest.of( 0, 10 ) ).blockFirst() ).isEqualTo( IRIS );
	}

	@Test
	public void testRetrieveBySpeciesPageable() {
		irisRepository.save( IRIS ).block();
		assertThat( irisRepository.retrieveBySpeciesPageable( "setosa", PageRequest.of( 0, 10 ) ).blockFirst() ).isEqualTo( IRIS );
	}

	@Test
	public void testDeleteIris() {
		irisRepository.save( IRIS ).block();
		assertThat( irisRepository.deleteById( IRIS.getId() ).blockOptional() ).isNotNull();
	}
}