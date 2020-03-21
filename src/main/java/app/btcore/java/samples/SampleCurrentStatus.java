package app.btcore.java.samples;

import app.btcore.java.Bitcapital;
import app.btcore.java.MainApplication;
import app.btcore.java.model.UserModel;
import app.btcore.java.model.status.ServerStatus;

import java.io.IOException;

public class SampleCurrentStatus {

    public static void getCurrentStatus(Bitcapital bitcapital) throws IOException {
        System.out.println("\nFetching current server status from Bit Capital APIs...\n");
        ServerStatus currentStatus = bitcapital.status().current().execute().body();

        if (currentStatus != null) {
            // Print basic API status
            System.out.println("- API URL: " + bitcapital.getApiUrl());
            System.out.println("- API Version: " + currentStatus.name + ":" + currentStatus.version);
        }
    }
}
