package Client.Backend;

import java.util.HashMap;


public class Response {
    String status;
    String path;
    HashMap<String, String> props;
    Response(String status, String path, HashMap<String, String> props) {
        this.status = status;
        this.path = path;
        this.props = props;
    } 
}
