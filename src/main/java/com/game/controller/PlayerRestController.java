package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/players/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long playerId) {

        if (playerId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Player player = playerService.getById(playerId);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "/players", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> savePlayer(@RequestBody @Validated Player player) {         //@Valid
        HttpHeaders headers = new HttpHeaders();

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        player.setLevel(levelPlayer(player.getExperience()));

        player.setUntilNextLevel(untilNextLevel(player.getLevel(), player.getExperience()));

        this.playerService.save(player);
        return new ResponseEntity<>(player, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/players/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //method POST
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, UriComponentsBuilder builder) {
        HttpHeaders headers = new HttpHeaders();

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.playerService.save(player);
        return new ResponseEntity<>(player, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/players/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {
        Player player = this.playerService.getById(id);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Player>> getAllPlayers() {

        List<Player> players = this.playerService.getAll();

        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @RequestMapping(value = "/players/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Integer> getCountPlayers() {

        List<Player> players = this.playerService.getAll();

        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(players.size(), HttpStatus.OK);
    }

    private static Integer levelPlayer(int experience){
        int level = (int) ((Math.sqrt((2500 + 200 * experience)) - 50) / 100);
        return level;
    }

    private static Integer untilNextLevel(int level, int experience){
        int untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        return untilNextLevel;
    }
}
