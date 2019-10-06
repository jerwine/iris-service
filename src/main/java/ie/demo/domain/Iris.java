package ie.demo.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Iris {

	@Id
	private String id;

	private String species;
	private BigDecimal sepalLength;
	private BigDecimal sepalWidth;
	private BigDecimal petalLength;
	private BigDecimal petalWidth;
}