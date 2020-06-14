package com.ujjwalsingh.busticket;

public class Users {
    private String id;
    private Integer hasticketthree;
    private Integer hasticketfive;
    private Integer hasticketsix;
    private String username;

    public Users(String id, Integer hasticketthree, Integer hasticketfive, Integer hastickesix, String username) {
        this.id = id;
        this.hasticketthree = hasticketthree;
        this.hasticketfive = hasticketfive;
        this.hasticketsix = hasticketsix;
        this.username = username;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHasticketthree() {
        return hasticketthree;
    }

    public void setHastickethree(Integer hasticketthree) {
        this.hasticketthree = hasticketthree;
    }

    public Integer getHasticketfive() {
        return hasticketfive;
    }

    public void setHasticketfive(Integer hasticketfive) {
        this.hasticketfive = hasticketfive;
    }

    public Integer getHasticketsix() {
        return hasticketsix;
    }

    public void setHastickesix(Integer hastickesix) {
        this.hasticketsix = hastickesix;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
