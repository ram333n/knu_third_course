package org.example.util;

import org.example.model.Player;
import org.example.model.Team;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public final class IoUtils {

    private IoUtils() {}

    public static void writeString(DataOutputStream out, String str) throws IOException {
        out.writeInt(str.length());
        out.writeBytes(str);
    }

    public static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        return new String(data);
    }

    public static Player readPlayer(DataInputStream in) throws IOException {
        Player player = new Player();
        player.setId(in.readLong());
        player.setTeamId(in.readLong());
        player.setName(readString(in));
        player.setPrice(new BigDecimal(readString(in)));

        return player;
    }

    public static Team readTeam(DataInputStream in) throws IOException {
        Team team = new Team();
        team.setId(in.readLong());
        team.setName(readString(in));
        team.setCountry(readString(in));

        return team;
    }

    public static void writePlayer(DataOutputStream out, Player player, boolean withId) throws IOException {
        if (withId) {
            out.writeLong(player.getId());
        }

        out.writeLong(player.getTeamId());
        writeString(out, player.getName());
        writeString(out, player.getPrice().toString());
    }

    public static void writeTeam(DataOutputStream out, Team team, boolean withId) throws IOException {
        if (withId) {
            out.writeLong(team.getId());
        }

        writeString(out, team.getName());
        writeString(out, team.getCountry());
    }
}
