package de.frinshhd.core;

import com.j256.ormlite.field.DatabaseField;

import java.math.BigInteger;
import java.util.UUID;

public class Ban {
    public int id;

    public UUID uuid;

    public long banTime;

    public long unbanTime;

    public String reason;

    public UUID banner;

    public boolean disabled;
}
