package ie.demo.bootstrap.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ie.demo.bootstrap.model.IrisCSV;
import ie.demo.domain.Iris;

@Mapper
public interface IrisCSVMapper {

	IrisCSVMapper INSTANCE = Mappers.getMapper( IrisCSVMapper.class );

	IrisCSV toIrisCSV( Iris iris );

	Iris toIris( IrisCSV irisCSV );
}