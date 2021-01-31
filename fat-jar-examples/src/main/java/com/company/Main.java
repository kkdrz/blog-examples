package com.company;

import com.company.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Client client = readClientDataFromCli();

        writeClientToJson(client, "client.json");
    }

    private static Client readClientDataFromCli() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert client data.");

        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Surname: ");
        String surname = sc.next();

        System.out.print("Email: ");
        String email = sc.next();

        return new Client(name, surname, email);
    }

    private static void writeClientToJson(Client client, String fileName) throws IOException {
        File outputFile = new File(fileName);
        outputFile.createNewFile();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputFile, client);
    }
}
