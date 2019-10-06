package ie.demo.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ie.demo.api.model.IrisDTO;
import ie.demo.domain.Iris;

@Mapper
public interface IrisMapper {

	IrisMapper INSTANCE = Mappers.getMapper( IrisMapper.class );

	IrisDTO toIrisDTO( Iris iris );

	Iris toIris( IrisDTO irisDTO );
}