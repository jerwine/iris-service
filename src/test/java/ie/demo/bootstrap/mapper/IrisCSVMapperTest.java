package ie.demo.bootstrap.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ie.demo.bootstrap.model.IrisCSV;
import ie.demo.domain.Iris;

public class IrisCSVMapperTest {

	static final Iris IRIS = Iris.builder()
		.petalLength( BigDecimal.ONE )
		.petalWidth( BigDecimal.ONE )
		.sepalLength( BigDecimal.ONE )
		.sepalWidth( BigDecimal.ONE )
		.species( "foo" )
		.build();

	static final IrisCSV IRIS_CSV = IrisCSV.builder()
		.petalLength( BigDecimal.ONE )
		.petalWidth( BigDecimal.ONE )
		.sepalLength( BigDecimal.ONE )
		.sepalWidth( BigDecimal.ONE )
		.species( "foo" )
		.build();

	@Test
	public void toIrisCSV() {

		IrisCSV irisCSV = IrisCSVMapper.INSTANCE.toIrisCSV( IRIS );

		assertThat( IRIS.getPetalLength() ).isEqualTo( irisCSV.getPetalLength() );
		assertThat( IRIS.getPetalWidth()  ).isEqualTo( irisCSV.getPetalWidth()  );
		assertThat( IRIS.getSepalLength() ).isEqualTo( irisCSV.getSepalLength() );
		assertThat( IRIS.getSepalWidth()  ).isEqualTo( irisCSV.getSepalWidth()  );
		assertThat( IRIS.getSpecies()     ).isEqualTo( irisCSV.getSpecies()     );
	}

	@Test
	public void toIris() {

		Iris iris = IrisCSVMapper.INSTANCE.toIris( IRIS_CSV );

		assertThat( IRIS_CSV.getPetalLength() ).isEqualTo( iris.getPetalLength() );
		assertThat( IRIS_CSV.getPetalWidth()  ).isEqualTo( iris.getPetalWidth()  );
		assertThat( IRIS_CSV.getSepalLength() ).isEqualTo( iris.getSepalLength() );
		assertThat( IRIS_CSV.getSepalWidth()  ).isEqualTo( iris.getSepalWidth()  );
		assertThat( IRIS_CSV.getSpecies()     ).isEqualTo( iris.getSpecies()     );
	}
}