package app.btcore.java.model;

import app.btcore.java.model.states.BaseStatefulModel;
import app.btcore.java.model.status.ConsumerStatus;

public class ConsumerModel extends BaseStatefulModel<ConsumerStatus> {

    public static enum Type {
        personal,
        corporate;
    }

    public String taxId;

    public Type type;

}
