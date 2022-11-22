package org.example.util;

import org.example.model.Player;
import org.example.model.Team;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

public final class IoUtils {

    private IoUtils() {}

    public static void writeInt(ByteArrayOutputStream out, int number) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(number);
        out.write(buffer.array());
    }

    public static int readInt(ByteArrayInputStream in) throws IOException {
        return getBufferFromNBytes(in, Integer.BYTES).getInt();
    }

    public static void writeLong(ByteArrayOutputStream out, Long number) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(number);
        out.write(buffer.array());
    }

    public static Long readLong(ByteArrayInputStream in) throws IOException {
        return getBufferFromNBytes(in, Long.BYTES).getLong();
    }

    private static ByteBuffer getBufferFromNBytes(ByteArrayInputStream in, int length) throws IOException {
        byte[] bytes = in.readNBytes(length);
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    public static void writeString(DataOutputStream out, String str) throws IOException {
        out.writeInt(str.length());
        out.writeBytes(str);
    }

    public static void writeString(ByteArrayOutputStream out, String str) throws IOException {
        int length = str.length();
        out.write(length);
        out.write(str.getBytes());
    }

    public static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        return new String(data);
    }

    public static String readString(ByteArrayInputStream in) throws IOException {
        int length = readInt(in);
        return new String(in.readNBytes(length));
    }

    public static void writePlayer(DataOutputStream out, Player player, boolean withId) throws IOException {
        if (withId) {
            out.writeLong(player.getId());
        }

        out.writeLong(player.getTeamId());
        writeString(out, player.getName());
        writeString(out, player.getPrice().toString());
    }

    public static void writePlayer(ByteArrayOutputStream out, Player player, boolean withId) throws IOException {
        if (withId) {
            writeLong(out, player.getId());
        }

        writeLong(out, player.getTeamId());
        writeString(out, player.getName());
        writeString(out, player.getPrice().toString());
    }

    public static Player readPlayer(DataInputStream in, boolean withId) throws IOException {
        Player player = new Player();

        if (withId) {
            player.setId(in.readLong());
        }

        player.setTeamId(in.readLong());
        player.setName(readString(in));
        player.setPrice(new BigDecimal(readString(in)));

        return player;
    }

    public static Player readPlayer(ByteArrayInputStream in, boolean withId) throws IOException {
        Player player = new Player();

        if (withId) {
            player.setId(readLong(in));
        }

        player.setTeamId(readLong(in));
        player.setName(readString(in));
        player.setPrice(new BigDecimal(readString(in)));

        return player;
    }

    public static void writeTeam(DataOutputStream out, Team team, boolean withId) throws IOException {
        if (withId) {
            out.writeLong(team.getId());
        }

        writeString(out, team.getName());
        writeString(out, team.getCountry());
    }

    public static void writeTeam(ByteArrayOutputStream out, Team team, boolean withId) throws IOException {
        if (withId) {
            writeLong(out, team.getId());
        }

        writeString(out, team.getName());
        writeString(out, team.getCountry());
    }

    public static Team readTeam(DataInputStream in, boolean withId) throws IOException {
        Team team = new Team();

        if (withId) {
            team.setId(in.readLong());
        }

        team.setName(readString(in));
        team.setCountry(readString(in));

        return team;
    }
}
