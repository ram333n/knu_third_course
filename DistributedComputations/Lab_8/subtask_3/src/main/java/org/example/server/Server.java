package org.example.server;

import com.rabbitmq.client.*;
import org.example.Constants;
import org.example.dao.PlayerDao;
import org.example.dao.TeamDao;
import org.example.model.Player;
import org.example.model.Team;
import org.example.util.IoUtils;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class Server {
    private final TeamDao teamDao;
    private final PlayerDao playerDao;
    private final int port;
    private Channel channel;

    public Server(int port) {
        this.teamDao = new TeamDao();
        this.playerDao = new PlayerDao();
        this.port = port;
    }

    public void start() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        factory.setPort(port);

        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(Constants.QUEUE, false, false, false, null);
        channel.queuePurge(Constants.QUEUE);
        channel.basicQos(1);

        System.out.println("Awaiting requests");

        processQuery();
    }

    private void processQuery() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            DataInputStream in = new DataInputStream(new ByteArrayInputStream(delivery.getBody()));
            ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(outBytes);

            try {
                String query = IoUtils.readString(in);
                System.out.println(query);
                switch (query) {
                    case "insertTeam" -> {
                        Team toInsert = IoUtils.readTeam(in, false);
                        boolean result = teamDao.insert(toInsert);
                        out.writeBoolean(result);
                    }

                    case "insertPlayer" -> {
                        Player toInsert = IoUtils.readPlayer(in, false);
                        boolean result = playerDao.insert(toInsert);
                        out.writeBoolean(result);
                    }

                    case "deletePlayer" -> {
                        Long toDelete = in.readLong();
                        boolean result = playerDao.deleteById(toDelete);
                        out.writeBoolean(result);
                    }

                    case "findPlayersByTeamName" -> {
                        String teamName = IoUtils.readString(in);
                        List<Player> result = playerDao.findByTeamName(teamName);
                        writeListOfPlayers(out, result);
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("[.]" + e);
            } finally {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, outBytes.toByteArray());
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        channel.basicConsume(Constants.QUEUE, false, deliverCallback, consumerTag -> {});
    }

    private void writeListOfPlayers(DataOutputStream out, List<Player> players) throws IOException {
        out.writeInt(players.size());

        for (Player player : players) {
            IoUtils.writePlayer(out, player, true);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(Constants.PORT);
            server.start();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
