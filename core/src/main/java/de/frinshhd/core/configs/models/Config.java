package de.frinshhd.core.configs.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.crypto.Data;

public class Config {

    @JsonProperty
    public Database database;

    @JsonProperty
    public boolean debug;

}
