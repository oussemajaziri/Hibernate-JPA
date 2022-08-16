package com.hibernate.formation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibernate.formation.config.PersistenceConfig;
import com.hibernate.formation.domain.Movie;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@SqlConfig (dataSource = "dataSourceH2",transactionManager = "transactionManager")
@Sql ("/datas/data-test.sql")
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository repository;
	
	@Test
	public void save_casnominal() {
		Movie movie = new Movie();
		movie.setName("Inception");
		repository.persist(movie);
	}
	
	@Test
	public void merge_casnSimule() {
		Movie movie = new Movie();
		movie.setName("Inception 2");
		movie.setId(-1L);
		Movie mergedMovie = repository.merge(movie);
		assertThat(mergedMovie.getName()).as("film mergé").isEqualTo("Inception 2");
	}
	
	@Test
	public void find_casbominal() {
		Movie memento = repository.find(-2L);
		assertThat(memento.getName()).as("film récuperé").isEqualTo("memento");
	}
	
	@Test
	public void getAll_casbominal() {
		List<Movie> movies = repository.getAll();
		assertThat(movies).as("film récuperés").hasSize(2);
	}
	
	@Test
	public void remove_casbominal() {
		repository.remove(-2L);
		List<Movie> movies = repository.getAll();
		assertThat(movies).as("un film suprimé").hasSize(1);
	}
	
	@Test
	public void getReference_casNominal() {
		Movie movie = repository.getReference(-2L);
		assertThat(movie.getId()).as("le film n'a pas eté correctement chargé").isEqualTo(-2L);
	}
	
}
