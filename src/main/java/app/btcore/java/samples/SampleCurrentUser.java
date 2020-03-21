package app.btcore.java.samples;

import app.btcore.java.Bitcapital;
import app.btcore.java.model.UserModel;

import java.io.IOException;

public class SampleCurrentUser {

    public static UserModel getCurrentUser(Bitcapital bitcapital) throws IOException {
        System.out.println("\nFetching current user from Bit Capital APIs...\n");
        UserModel user = bitcapital.getClient().get("/users/me", UserModel.class);

        if (user != null) {
            // Print basic API status
            System.out.println("- User ID: " + user.id);
            System.out.println("- User Name: " + user.name);
            System.out.println("- User Role: " + user.role);
            System.out.println("- User Status: " + user.status);
        }

        return user;
    }
}
