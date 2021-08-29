package com.game.controller;

import com.game.entity.Player;
import com.game.entity.PlayersFilter;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping(path = "/players/{id}", produces = "application/json")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") String stringId) {
        Long id = null;

        if (playerService.idIsNotValid(stringId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        id = Long.parseLong(stringId);

        Optional<Player> player = playerService.getById(id);
        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/players", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {

        if (playerService.playerIsValid(player)) {
            return new ResponseEntity<>(playerService.create(player), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/players/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable("id") String stringId) {

        Long id;

        if (playerService.idIsNotValid(stringId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        id = Long.parseLong(stringId);

        Optional<Player> optionalPlayer = playerService.getById(id);
        if (!optionalPlayer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (player != null && playerService.upgradeIsNotValid(player, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if (playerService.playerIsValid(player)) {
            return new ResponseEntity<>(this.playerService.edit(player, id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/players/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {
        Optional<Player> player = playerService.getById(id);
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!player.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/players", produces = "application/json")
    public ResponseEntity<List<Player>> getPlayers(@RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "title", required = false) String title,
                                                   @RequestParam(name = "race", required = false) String race,
                                                   @RequestParam(name = "profession", required = false) String profession,
                                                   @RequestParam(name = "after", required = false) Long after,
                                                   @RequestParam(name = "before", required = false) Long before,
                                                   @RequestParam(name = "banned", required = false) Boolean banned,
                                                   @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(name = "maxLevel", required = false) Integer maxLevel,
                                                   @RequestParam(name = "order", defaultValue = "id", required = false) String order,
                                                   @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize) {

        PlayersFilter playersFilter = new PlayersFilter()
                .setName(name)
                .setTitle(title)
                .setRace(race)
                .setProfession(profession)
                .setAfter(after)
                .setBefore(before)
                .setBanned(banned)
                .setMinExperience(minExperience)
                .setMaxExperience(maxExperience)
                .setMinLevel(minLevel)
                .setMaxLevel(maxLevel);

        return new ResponseEntity<>(playerService.getAll(playersFilter, pageNumber, pageSize, order), HttpStatus.OK);
    }

    @GetMapping(path = "/players/count", produces = "application/json")
    public ResponseEntity<Integer> getPlayersCount(@RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "title", required = false) String title,
                                                   @RequestParam(name = "race", required = false) String race,
                                                   @RequestParam(name = "profession", required = false) String profession,
                                                   @RequestParam(name = "after", required = false) Long after,
                                                   @RequestParam(name = "before", required = false) Long before,
                                                   @RequestParam(name = "banned", required = false) Boolean banned,
                                                   @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(name = "maxLevel", required = false) Integer maxLevel,
                                                   @RequestParam(name = "order", defaultValue = "id", required = false) String order,
                                                   @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        PlayersFilter playersFilter = new PlayersFilter()
                .setName(name)
                .setTitle(title)
                .setRace(race)
                .setProfession(profession)
                .setAfter(after)
                .setBefore(before)
                .setBanned(banned)
                .setMinExperience(minExperience)
                .setMaxExperience(maxExperience)
                .setMinLevel(minLevel)
                .setMaxLevel(maxLevel);

        return new ResponseEntity<>(playerService.getCount(playersFilter, pageNumber, pageSize, order), HttpStatus.OK);
    }
}
