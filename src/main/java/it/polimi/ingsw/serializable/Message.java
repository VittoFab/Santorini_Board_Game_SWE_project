package it.polimi.ingsw.serializable;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Allows to serialize and translate message methods sent by the server to the client over the network.
 * Every constructor represents a different type of message the server can send.
 */
public class Message implements Serializable {

    private final int type; //says which parameters the method takes
    private final String method;
    private String stringParam;
    private String stringParam2;
    private int intParam1;
    private int intParam2;
    private ArrayList<String> stringListParam;
    private CellClient toUpdateCell;
    private ArrayList<WorkerClient> workersParam;
    private WorkerClient worker;

    public static final int NO_PARAMETERS = 1;
    public static final int STRING = 2;
    public static final int STRING_ARRAYLIST = 3;
    public static final int TWO_INT = 4;
    public static final int CELL_CLIENT = 5;
    public static final int WORKER_CLIENT_ARRAYLIST_WORKER_CLIENT = 6;
    public static final int TWO_STRING = 7;
    public static final int ONE_INT = 8;


    public Message(String method) {
        type = NO_PARAMETERS;
        this.method = method;
    }

    public Message(String method, String string1) {
        type = STRING;
        this.method = method;
        this.stringParam = string1;
    }

    public Message(String method, ArrayList<String> stringListParam) {
        type = STRING_ARRAYLIST;
        this.method = method;
        this.stringListParam = stringListParam;
    }

    public Message(String method, int intParam1, int intParam2) {
        type = TWO_INT;
        this.method = method;
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
    }

    public Message(String method, CellClient toUpdateCell) {
        type = CELL_CLIENT;
        this.method = method;
        this.toUpdateCell = toUpdateCell;
    }

    public Message(String method, ArrayList<WorkerClient> workersParam, WorkerClient worker) {
        type = WORKER_CLIENT_ARRAYLIST_WORKER_CLIENT;
        this.method = method;
        this.workersParam = workersParam;
        this.worker = worker;
    }

    public Message(String method, String string1, String string2) {
        type = TWO_STRING;
        this.method = method;
        this.stringParam = string1;
        this.stringParam2 = string2;
    }

    public Message(String method, int numberOfPlayers) {
        type = ONE_INT;
        this.method = method;
        this.intParam1 = numberOfPlayers;
    }

    public String getMethod() {
        return method;
    }

    public String getStringParam() {
        return stringParam;
    }

    public String getStringParam2() {
        return stringParam2;
    }

    public int getIntParam1() {
        return intParam1;
    }

    public int getIntParam2() {
        return intParam2;
    }

    public ArrayList<String> getStringListParam() {
        return stringListParam;
    }

    public CellClient getToUpdateCell() {
        return toUpdateCell;
    }

    public ArrayList<WorkerClient> getWorkersParam() {
        return workersParam;
    }

    public WorkerClient getWorker() {
        return worker;
    }

    public int getMessageType() {
        return type;
    }


}
