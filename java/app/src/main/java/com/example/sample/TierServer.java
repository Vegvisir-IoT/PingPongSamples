package com.example.sample;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.example.sample.proto.SearchRequest;

public class ProtoServer {
    /**
     * Server will listen on 9191
     */

    public static void main(String[] args) throws Exception{
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Current IP address : " + ip);
            System.out.println("Current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        System.out.println("The server has started.");
        var pool = Executors.newFixedThreadPool(20);
        int clientNumber = 0;
        try (var listener =  new ServerSocket(9191)){
            while (true) {
                pool.execute(new myFunction(listener.accept(), clientNumber++));
            }
        }
    }

    private static class myFunction implements Runnable {
        private Socket socket;
        private int clientNumber;

        public myFunction(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.println("New Client #" + clientNumber + " connected at " + socket);
        }

        public void run() {
            byte[] message;
            try {
                //var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //InputStream in = socket.getInputStream();
                //ByteArrayOutputStream line = new ByteArrayOutputStream();
                String banner = "Server Response.";
                byte [] outgoing = banner.getBytes();
                SearchRequest request = SearchRequest.parseFrom( socket.getInputStream() );

                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                dOut.write( outgoing );
                System.out.println("Received the following message:");
                System.out.println("Query: " + request.getQuery());
                System.out.println("Results: " + request.getResultPerPage());
                System.out.println("Connection with client #" + clientNumber + " closed");

                //out.flush();
            } catch (Exception e){
                System.out.println("Error handling client #" + clientNumber);
            } finally {
                try { socket.close(); } catch (IOException e) {System.out.println("unable to close initially");}
                //System.out.println( "Received: "+ message );
            }
        }
    }
}

