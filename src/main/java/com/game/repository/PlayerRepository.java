package com.game.repository;

import com.game.entity.Player;
import com.game.entity.PlayersFilter;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // #{#pf.name}
    // https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions

    @Query("SELECT p FROM Player p WHERE " +
            "(:#{#pf.name} IS null or p.name LIKE :#{#pf.name}) AND " +
            "(:#{#pf.title} IS null or p.title LIKE :#{#pf.title}) AND " +
            "(:#{#pf.race} IS null or p.race = :#{#pf.race}) AND " +
            "(:#{#pf.profession} IS null or p.profession = :#{#pf.profession}) AND " +
            "(:#{#pf.after} IS null or p.birthday > :#{#pf.after}) AND " +
            "(:#{#pf.before} IS null or p.birthday < :#{#pf.before}) AND " +
            "(:#{#pf.banned} IS null or p.banned = :#{#pf.banned}) AND " +
            "(:#{#pf.minExperience} IS null or p.experience >= :#{#pf.minExperience}) AND " +
            "(:#{#pf.maxExperience} IS null or p.experience <= :#{#pf.maxExperience}) AND " +
            "(:#{#pf.minLevel} IS null or p.level >= :#{#pf.minLevel}) AND " +
            "(:#{#pf.maxLevel} IS null or p.level <= :#{#pf.maxLevel})"
    )
    Page<Player> findAll(@Param("pf") PlayersFilter pf, Pageable pageable);

}
