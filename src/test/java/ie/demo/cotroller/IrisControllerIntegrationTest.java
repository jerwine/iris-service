package ie.demo.cotroller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ie.demo.api.model.IrisDTO;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class IrisControllerIntegrationTest {

	@LocalServerPort
	int port;

	TestRestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void testGetIrises() throws JsonMappingException, JsonProcessingException {

		ResponseEntity<String> response = restTemplate.getForEntity( "http://localhost:" + port + "/irises", String.class );

		assertThat( response.getBody() ).isNotNull();
		assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );

		JsonNode responseJsonNode = new ObjectMapper().readTree( response.getBody() );

		assertThat( responseJsonNode.get( 0 ) ).isNotEmpty();

		assertThat( responseJsonNode.get( 0 ).get( "id"          ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "species"     ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "sepalLength" ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "sepalWidth"  ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "petalLength" ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "petalWidth"  ) ).isNotNull();

		assertThat( responseJsonNode.size() ).isEqualTo( 10 );
	}

	@Test
	public void testGetIrisById() throws JsonMappingException, JsonProcessingException {

		ResponseEntity<String> response = restTemplate.getForEntity( "http://localhost:" + port + "/irises", String.class );

		assertThat( response.getBody() ).isNotNull();
		assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );

		JsonNode responseJsonNode = new ObjectMapper().readTree( response.getBody() );
		String id = responseJsonNode.get( 0 ).get( "id" ).asText();

		ResponseEntity<String> irisResponse = restTemplate.getForEntity( "http://localhost:" + port + "/irises/" + id, String.class );
		JsonNode irisJsonNode = new ObjectMapper().readTree( irisResponse.getBody() );

		assertThat( irisJsonNode ).isNotNull();

		assertThat( irisJsonNode.get( "id" ) ).isNotNull();
		assertThat( irisJsonNode.get( "id" ).asText() ).isEqualTo( id );

		assertThat( irisJsonNode.get( "species"     ) ).isNotNull();
		assertThat( irisJsonNode.get( "sepalLength" ) ).isNotNull();
		assertThat( irisJsonNode.get( "sepalWidth"  ) ).isNotNull();
		assertThat( irisJsonNode.get( "petalLength" ) ).isNotNull();
		assertThat( irisJsonNode.get( "petalWidth"  ) ).isNotNull();
	}

	@Test
	public void testGetAllSpecies() throws JsonMappingException, JsonProcessingException {

		ResponseEntity<String> response = restTemplate.getForEntity( "http://localhost:" + port + "/irises/species", String.class );

		assertThat( response.getBody() ).isNotNull();
		assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );

		JsonNode responseJsonNode = new ObjectMapper().readTree( response.getBody() );

		assertThat( responseJsonNode.get( 0 ) ).isNotEmpty();
		assertThat( responseJsonNode.size() ).isEqualTo( 3 );

		assertThat( responseJsonNode.get( 0 ).get( "species" ) ).isNotNull();
		assertThat( responseJsonNode.get( 1 ).get( "species" ) ).isNotNull();
		assertThat( responseJsonNode.get( 2 ).get( "species" ) ).isNotNull();
	}

	@Test
	public void testGetIrisBySpecies() throws JsonMappingException, JsonProcessingException {

		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/irises/species/setosa", String.class );

		assertThat( response.getBody() ).isNotNull();
		assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );

		JsonNode responseJsonNode = new ObjectMapper().readTree( response.getBody() );

		assertThat( responseJsonNode.get( 0 ) ).isNotEmpty();

		assertThat( responseJsonNode.get( 0 ).get( "species" ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "species" ).asText() ).isEqualTo( "setosa" );

		assertThat( responseJsonNode.get( 0 ).get( "id"          ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "sepalLength" ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "sepalWidth"  ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "petalLength" ) ).isNotNull();
		assertThat( responseJsonNode.get( 0 ).get( "petalWidth"  ) ).isNotNull();

		assertThat( responseJsonNode.size() ).isEqualTo( 10 );
	}

	@Test
	public void testSaveOrUpdateIris() {

		IrisDTO irisDTO = IrisDTO.builder()
			.id( "1" )
			.species( "setosa" )
			.petalLength( BigDecimal.ZERO )
			.petalWidth( BigDecimal.ZERO )
			.sepalLength( BigDecimal.ZERO )
			.sepalWidth( BigDecimal.ZERO )
		.build();
		HttpEntity<IrisDTO> request = new HttpEntity<>( irisDTO, new HttpHeaders() );

		ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:" + port + "/irises", request, String.class);
		assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CREATED );
	}

	@Test
	public void testDeleteIris() {

		String id = "random";

		IrisDTO irisDTO = IrisDTO.builder()
			.id( id )
			.species( "setosa" )
			.petalLength( BigDecimal.ZERO )
			.petalWidth( BigDecimal.ZERO )
			.sepalLength( BigDecimal.ZERO )
			.sepalWidth( BigDecimal.ZERO )
		.build();
		HttpEntity<IrisDTO> request = new HttpEntity<>( irisDTO, new HttpHeaders() );

		restTemplate.postForEntity( "http://localhost:" + port + "/irises", request, String.class);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + port + "/irises/" + id, HttpMethod.DELETE, new HttpEntity( new HttpHeaders() ), String.class );

		assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
	}
}