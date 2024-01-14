package de.frinshhd.core.database.sql.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "Bans")
public class BanSQL {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public UUID playerID;

    @DatabaseField
    public long banTime;

    @DatabaseField
    public long unbanTime;

    @DatabaseField
    public String reason;

    @DatabaseField
    public UUID bannerID;

    @DatabaseField(defaultValue = "false")
    public boolean disabled;

    public BanSQL() {
    }

    public void create(UUID playerID, long banTime, long unbanTime, String reason, UUID bannerID) {
        this.playerID = playerID;
        this.banTime = banTime;
        this.unbanTime = unbanTime;
        this.reason = reason;
        this.bannerID = bannerID;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public void setDisabled() {
        this.disabled = true;
    }
}
