package ie.demo.bootstrap;

import static ie.demo.bootstrap.model.IrisCSV.IRIS_CSV_COLUMS;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import ie.demo.bootstrap.mapper.IrisCSVMapper;
import ie.demo.bootstrap.model.IrisCSV;
import ie.demo.repository.IrisRepository;

@Component
@Profile("dev")
public class Bootstrap implements CommandLineRunner {

	@Value("classpath:iris.csv")
	Resource resourceFile;

	final IrisRepository irisRepository;
	final IrisCSVMapper irisCSVMapper;

	public Bootstrap( IrisRepository irisRepository, IrisCSVMapper irisCSVMapper ) {
		this.irisRepository = irisRepository;
		this.irisCSVMapper = irisCSVMapper;
	}

	/*
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Transactional
	@Override
	public void run( String... args ) throws Exception {

		irisRepository.deleteAll().block();

		readFile().forEach( irisCSV -> { irisRepository.save( irisCSVMapper.toIris( irisCSV ) ).block(); } );
	}

	/**
	 * Load in and parse the iris.csv
	 * @return
	 * @throws IOException
	 */
	List<IrisCSV> readFile() throws IOException {

		/* Set up the column mapping */
		ColumnPositionMappingStrategy<IrisCSV>irisCSVstrategy = new ColumnPositionMappingStrategy<IrisCSV>();
		irisCSVstrategy.setType( IrisCSV.class );
		irisCSVstrategy.setColumnMapping( IRIS_CSV_COLUMS );

		/* Set up the reader of the CSV file */
		Reader reader = Files.newBufferedReader( Paths.get( resourceFile.getFile().toURI() ) );

		/* Built up the mapping */
		CsvToBean<IrisCSV> csvToBean = new CsvToBeanBuilder<IrisCSV>( reader )
			.withSkipLines( 1 )
			.withMappingStrategy( irisCSVstrategy )
			.build();

		/* Parse the file and return as a list */
		return csvToBean.parse();
	}
}