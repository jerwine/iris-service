package ie.demo.api.mapper;

import ie.demo.api.model.IrisDTO;

public interface IrisSpeciesMapper {

	IrisDTO toIrisDTO( String species );

	String toSpecies( IrisDTO irisDTO );
}