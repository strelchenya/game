package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    private static Integer levelPlayer(int experience) {
        int level = (int) ((Math.sqrt((2500 + 200 * experience)) - 50) / 100);
        return level;
    }

    private static Integer untilNextLevel(int level, int experience) {
        int untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        return untilNextLevel;
    }

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
    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "title", required = false) String title,
                                                      @RequestParam(value = "race", required = false) String race,
                                                      @RequestParam(value = "profession", required = false) String profession,
                                                      @RequestParam(value = "after", required = false) Long after,
                                                      @RequestParam(value = "before", required = false) Long before,
                                                      @RequestParam(value = "banned", required = false) Boolean banned,
                                                      @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                      @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                      @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                      @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                      @RequestParam(value = "order", required = false) String order,
                                                      @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        if (name != null){
            Page<Player> pagePlayer = playerService.getAll(pageNumber,pageSize, order.toLowerCase());
            List<Player> listPlayers = pagePlayer.getContent();
            return new ResponseEntity<>(playerService.findByNameContains(name), HttpStatus.OK);
        }
        if (order == null){
            order = PlayerOrder.ID.getFieldName();
        }

        if (pageNumber == null){
            pageNumber = 0;
        }

        if (pageSize == null){
            pageSize = 3;
        }

        Page<Player> pagePlayer = playerService.getAll(pageNumber,pageSize, order.toLowerCase());
        List<Player> listPlayers = pagePlayer.getContent();

        if (listPlayers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listPlayers, HttpStatus.OK);
    }

    @RequestMapping(value = "/players/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Integer> getCountPlayers() {

        int count = (int) this.playerService.countPlayers();

        /*if (count == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
