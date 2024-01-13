package de.frinshhd.core.database.sql.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "Players")
public class PlayerSQL {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DatabaseField(id = true)
    public UUID uuid;

    @DatabaseField
    public UUID playerUUID;

    @DatabaseField
    public String playerName;


    public PlayerSQL() {}

    public void create(String playerName, UUID playerUUID) {
        this.uuid = UUID.randomUUID();

        this.playerName = playerName.toLowerCase();
        this.playerUUID = playerUUID;
    }

    public void updatePlayerName(String playerName) {
        this.playerName = playerName.toLowerCase();
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
