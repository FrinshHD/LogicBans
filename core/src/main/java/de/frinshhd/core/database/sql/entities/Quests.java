package de.frinshhd.core.database.sql.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@DatabaseTable(tableName = "Quests")
public class Quests {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DatabaseField(generatedId = true)
    private BigInteger ID;

    @DatabaseField
    private UUID uuid;

    @DatabaseField
    private long banTime;

    @DatabaseField
    private long unbanTime;

    @DatabaseField
    private String reason;

    @DatabaseField
    private UUID banner;

    @DatabaseField(defaultValue = "false")
    private boolean disabled;

    public Quests() {
    }

    public void create(UUID uuid, long banTime, long unbanTime, String reason, UUID banner) {
        this.uuid = uuid;
        this.banTime = banTime;
        this.unbanTime = unbanTime;
        this.reason = reason;
        this.banner = banner;
    }

    public UUID getUUID() {
        return uuid;
    }
}
