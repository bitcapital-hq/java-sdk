package app.btcore.java.ws.model;

import java.util.Date;

public class BaseStatefulModel extends BaseModel {

    public String status;

    public State[] states;

    public static class State extends BaseModel {
        public String status;

    }
}
