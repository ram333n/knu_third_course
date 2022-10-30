package org.example;

import java.io.File;

public final class Constants {
    private Constants() {}

    // XML tags
    public static final String MANAGER = "Manager";
    public static final String TEAM = "Team";
    public static final String PLAYER = "Player";
    public static final String ID = "id";
    public static final String TEAM_ID = "teamId";
    public static final String NAME = "name";
    public static final String COUNTRY = "country";
    public static final String PRICE = "price";

    //Paths to files
    public static final String PATH_TO_XML =
            String.join(File.separator, ".", "src", "main", "resources", "input.xml");

    public static final String PATH_TO_XSD =
            String.join(File.separator, ".", "src", "main", "resources", "schema.xsd");
}
