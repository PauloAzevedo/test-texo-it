package com.texoit.test.repositories;

import com.texoit.test.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT t FROM Movie t WHERE t.winner = :winner")
    public List<Movie> getAllWinners(@Param("winner") String winner);
}
