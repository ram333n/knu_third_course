package org.example;

import org.example.util.PathBuilder;
import org.example.validators.XSDValidator;

public class Main {
    public static void main(String[] args) {
        System.out.println(new XSDValidator().validate(PathBuilder.getPath(".", "src", "main", "resources", "input.xml"),
                                                       PathBuilder.getPath(".", "src", "main", "resources", "schema.xsd")));
    }
}