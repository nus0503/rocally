package com.company.rocally.controller;

import java.util.List;

public class TeletubbiesVO {

    private String name;
    private String color;
    private List<TeletubbiesVO> teletubbiesList;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public List<TeletubbiesVO> getTeletubbiesList() {
        return teletubbiesList;
    }
    public void setTeletubbiesList(List<TeletubbiesVO> teletubbiesList) {
        this.teletubbiesList = teletubbiesList;
    }
}
