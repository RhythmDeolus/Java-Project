package Server.Backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.reflect.Field;

public class Response<T> {
    T obj;
    public Response(T obj) {
        this.obj = obj;
    }

    public static void sendTextResponse(Socket clientSocket, String path, int nlines, String res_text) throws IOException {
        String res_str = "MYPROTOCOL 200\n";
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        res_str += path + " ";

        res_str += nlines + "\n";
        
        res_str += res_text;

        // System.out.println(res_text);
        out.writeUTF(res_str);
    }

    public void sendResponse(Socket clientSocket, String path) throws IOException {
        String res_str = "MYPROTOCOL 200\n";
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        res_str += path + " ";
        Field[] fields = obj.getClass().getFields();

        res_str += fields.length + "\n";
        
        for(int i = 0; i < fields.length; i++) {
            try {
                res_str += fields[i].getName() + "=" + fields[i].get(obj) + "\n";
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // System.out.println(res_str);
        out.writeUTF(res_str);
    
    }
}