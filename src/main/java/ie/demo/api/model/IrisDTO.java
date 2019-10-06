package ie.demo.api.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrisDTO {

	private String id;
	private String species;
	private BigDecimal sepalLength;
	private BigDecimal sepalWidth;
	private BigDecimal petalLength;
	private BigDecimal petalWidth;
}