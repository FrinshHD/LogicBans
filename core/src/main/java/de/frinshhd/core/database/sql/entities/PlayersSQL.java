package de.frinshhd.core.database.sql.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "Players")
public class PlayersSQL {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DatabaseField(id = true)
    public UUID uuid;

    @DatabaseField
    public UUID playerUUID;

    @DatabaseField
    public String playerName;


    public PlayersSQL() {}

    public UUID create(String playerName, UUID playerUUID) {
        this.uuid = UUID.randomUUID();

        this.playerName = playerName;
        this.playerUUID = playerUUID;

        return this.uuid;
    }

    public void updatePlayerName(String playerName) {
        this.playerName = playerName;
    }
}
