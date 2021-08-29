package com.game.entity;

import java.util.Date;

public class PlayersFilter {

    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date after;
    private Date before;
    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;

    public PlayersFilter() {
    }

    public String getName() {
        return name;
    }

    public PlayersFilter setName(String name) {
        this.name = (name == null) ? null : '%' + name + '%';
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PlayersFilter setTitle(String title) {
        this.title = (title == null) ? null : '%' + title + '%';
        return this;
    }

    public Race getRace() {
        return race;
    }

    public PlayersFilter setRace(String race) {
        this.race = (race == null) ? null : Race.valueOf(race);
        return this;
    }

    public Profession getProfession() {
        return profession;
    }

    public PlayersFilter setProfession(String profession) {
        this.profession = (profession == null) ? null : Profession.valueOf(profession);
        return this;
    }

    public Date getAfter() {
        return after;
    }

    public PlayersFilter setAfter(Long after) {
        this.after = (after == null) ? null : new Date(after);
        return this;
    }

    public Date getBefore() {
        return before;
    }

    public PlayersFilter setBefore(Long before) {
        this.before = (before == null) ? null : new Date(before);
        return this;
    }

    public Boolean getBanned() {
        return banned;
    }

    public PlayersFilter setBanned(Boolean banned) {
        this.banned = banned;
        return this;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public PlayersFilter setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
        return this;
    }

    public Integer getMaxExperience() {
        return maxExperience;
    }

    public PlayersFilter setMaxExperience(Integer maxExperience) {
        this.maxExperience = maxExperience;
        return this;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public PlayersFilter setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
        return this;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public PlayersFilter setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }
}
