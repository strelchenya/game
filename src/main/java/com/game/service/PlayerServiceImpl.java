package com.game.service;

import com.game.entity.Player;
import com.game.entity.PlayersFilter;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    @Transactional
    public Optional<Player> getById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    @Transactional
    public Player create(Player player) {
        player.setLevel(getCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(getExpForNextLevel(player.getLevel(), player.getExperience()));
        return playerRepository.save(player);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Player> getAll(PlayersFilter playersFilter, Integer pageNumber, Integer pageSize, String order) {
        Pageable paging = PageRequest.of(pageNumber, pageSize,
                                                        Sort.by(order.toLowerCase(Locale.ROOT)));
        return playerRepository.findAll(playersFilter, paging).getContent();
    }

    @Override
    @Transactional
    public Integer getCount(PlayersFilter playersFilter, Integer pageNumber, Integer pageSize, String order) {
        Pageable paging = PageRequest.of(pageNumber, pageSize,
                                                        Sort.by(order.toLowerCase(Locale.ROOT)));
        return (int) playerRepository.findAll(playersFilter, paging).getTotalElements();
    }

    private Integer getCurrentLevel(Integer currentExp) {
        return (int) (Math.sqrt(2500 + 200 * currentExp) - 50) / 100;
    }

    private Integer getExpForNextLevel(Integer currentLvl, Integer currentExp) {
        return 50 * (currentLvl + 1) * (currentLvl + 2) - currentExp;
    }

    @Override
    public boolean playerIsValid(Player player) {

        return player.getName() != null &&
                player.getTitle() != null &&
                player.getRace() != null &&
                player.getProfession() != null &&
                player.getBirthday() != null &&
                player.getExperience() != null &&
                player.getName().length() <= 12 &&
                player.getTitle().length() <= 30 &&
                !player.getName().isEmpty() &&
                player.getExperience() >= 0 &&
                player.getExperience() <= 10_000_000 &&
                player.getBirthday().after(new Date(946684799000L)) &&
                player.getBirthday().before(new Date(32503680000000L));

    }

    @Override
    @Transactional
    public Player edit(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public boolean idIsNotValid(String stringId) {
        Long id;

        try {
            id = Long.parseLong(stringId);
        } catch (Exception e) {
            return true;
        }

        return id != null && id <= 0;
    }

    @Override
    public boolean upgradeIsNotValid(Player player) {

        return player.getName() == null &&
                player.getTitle() == null &&
                player.getRace() == null &&
                player.getProfession() == null &&
                player.getBirthday() == null &&
                player.getExperience() == null &&
                player.getBanned() == null;
    }

    @Override
    public Player update(Player player, Player repositoryPlayer) {
        if (player.getName() != null && player.getName().length() <= 12) {
            repositoryPlayer.setName(player.getName());
        }

        if (player.getTitle() != null && player.getTitle().length() <= 30) {
            repositoryPlayer.setTitle(player.getTitle());
        }

        if (player.getRace() != null) {
            repositoryPlayer.setRace(player.getRace());
        }

        if (player.getProfession() != null) {
            repositoryPlayer.setProfession(player.getProfession());
        }

        if (player.getBirthday() != null) {
            repositoryPlayer.setBirthday(player.getBirthday());
        }

        if (player.getBanned() != null && player.getBirthday().after(new Date(946684799000L)) &&
                player.getBirthday().before(new Date(32503680000000L))) {
            repositoryPlayer.setBanned(player.getBanned());
        }

        if (player.getExperience() != null && player.getExperience() >= 0 && player.getExperience() <= 10_000_000) {
            repositoryPlayer.setExperience(player.getExperience());
            repositoryPlayer.setLevel(getCurrentLevel(repositoryPlayer.getExperience()));
            repositoryPlayer.setUntilNextLevel(getExpForNextLevel(repositoryPlayer.getLevel(),
                                                                    repositoryPlayer.getExperience()));
        }

        return repositoryPlayer;
    }

    @Override
    public boolean IsNotCorrectlyValue(Player player) {
        if (player.getName() == null) {
        } else {
            if (player.getName().isEmpty() || player.getName().length() > 12) {
                return true;
            }
        }

        if (player.getTitle() == null) {
        } else {
            if (player.getTitle().isEmpty() || player.getTitle().length() > 30) {
                return true;
            }
        }

        if (player.getRace() == null) {
        } else {
            if (player.getRace().ordinal() < 0 || player.getRace().ordinal() > Race.values().length) {
                return true;
            }
        }

        if (player.getProfession() == null) {
        } else {
            if (player.getProfession().ordinal() < 0 || player.getProfession().ordinal() > Profession.values().length) {
                return true;
            }
        }

        if (player.getBirthday() == null) {
        } else {
            if (!player.getBirthday().after(new Date(946684799000L)) ||
                    !player.getBirthday().before(new Date(32503680000000L))) {
                return true;
            }
        }

        if (player.getExperience() == null) {
        } else {
            if (player.getExperience() < 0 || player.getExperience() > 10_000_000) {
                return true;
            }
        }

        return false;
    }
}
