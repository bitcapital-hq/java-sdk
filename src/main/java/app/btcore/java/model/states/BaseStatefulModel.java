package app.btcore.java.model.states;

import app.btcore.java.model.BaseModel;

import java.util.List;

public class BaseStatefulModel<Status> extends BaseModel {

    public Status status;

    public List<BaseStateModel<Status>> states;
}
