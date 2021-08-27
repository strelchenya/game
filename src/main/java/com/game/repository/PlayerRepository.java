package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByNameAndTitleAndRaceAndProfession(String name, String title, String race, String profession);
    List<Player> findByNameContains(String name);
}
