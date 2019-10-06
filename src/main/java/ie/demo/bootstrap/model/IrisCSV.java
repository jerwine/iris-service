package ie.demo.bootstrap.model;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrisCSV {

	public final static String[] IRIS_CSV_COLUMS = new String[] { "sepal_length","sepal_width","petal_length","petal_width","species" };

	@CsvBindByPosition(	position = 0, locale = "en" )
	@CsvNumber(value = "0.##")
	private BigDecimal sepalLength;
	@CsvBindByPosition(	position = 1, locale = "en" )
	@CsvNumber(value = "0.##")
	private BigDecimal sepalWidth;
	@CsvBindByPosition(	position = 2, locale = "en" )
	@CsvNumber(value = "0.##")
	private BigDecimal petalLength;
	@CsvBindByPosition(	position = 3, locale = "en" )
	@CsvNumber(value = "0.##")
	private BigDecimal petalWidth;
	@CsvBindByPosition(	position = 4, locale = "en" )
	private String species;
}