package de.frinshhd.core.configs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.frinshhd.core.utils.DatabaseTypes;

public class Database {

    @JsonProperty
    private String type = "sqlite";

    @JsonProperty
    public String username = null;

    @JsonProperty
    public String password = null;

    public DatabaseTypes getType() {
        return DatabaseTypes.valueOf(type.toUpperCase());
    }

}
