package Server.Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

public class Request {
    public String path;
    public HashMap<String, String> props;
    public String type;
    public  Request(String path, HashMap<String, String> props, String type) {
        this.path = path;
        this.props = props;
        this.type = type;
    }
    public static Request getRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String line = in.readLine();
        String[] parts = line.split(" ");
        if (line != null && parts.length > 1) {
            String type = parts[1];
            line = in.readLine();
            System.out.println(line);
            parts = line.split(" ");
            String path = parts[0];
            int noLines = Integer.parseInt(parts[1]);


            HashMap<String, String> properties = new HashMap<String, String>();

            for(int i = 0; i < noLines; i++) {
                String temp_s = in.readLine();
                // System.out.println(temp_s);
                String[] temp = temp_s.split("=");

                if (temp.length <= 1) continue;
                properties.put(temp[0], temp[1]);
            }
            return new Request(path, properties, type);

        }
        return null;
    }
}
