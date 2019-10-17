package ie.demo.api.mapper.impl;

import org.springframework.stereotype.Component;

import ie.demo.api.mapper.IrisSpeciesMapper;
import ie.demo.api.model.IrisDTO;

@Component
public class IrisSpeciesMapperImpl implements IrisSpeciesMapper {

	public IrisDTO toIrisDTO( String species ) {
		if ( species == null ) {
			return null;
		} else {
			IrisDTO irisDTO = new IrisDTO();
			irisDTO.setSpecies(species);
			return irisDTO;
		}
	}

	public String toSpecies( IrisDTO irisDTO ) {
		if ( irisDTO == null ) {
			return null;
		} else {
			return irisDTO.getSpecies();
		}
	}
}