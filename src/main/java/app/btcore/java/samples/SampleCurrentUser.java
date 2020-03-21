package app.btcore.java.samples;

import app.btcore.java.Bitcapital;
import app.btcore.java.model.UserModel;

import java.io.IOException;

public class SampleCurrentUser {

    public static void getCurrentUser(Bitcapital bitcapital) throws IOException {
        UserModel user = bitcapital.getClient().get("/users/me", UserModel.class);

        if (user != null) {
            // Print basic API status
            System.out.println("\nStarting sample script for Bit Capital APIs...\n");
            System.out.println("- User ID: " + user.id);
            System.out.println("- User Name: " + user.name);
            System.out.println("- User Role: " + user.role);
            System.out.println("- User Status: " + user.status);
            System.out.println("\n");
        }
    }
}
