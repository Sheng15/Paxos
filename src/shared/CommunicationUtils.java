package shared;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CommunicationUtils {

    public static Result makeRequest(ObjectInputStream is, ObjectOutputStream os, RequestType reqType) throws
            IOException, ClassNotFoundException {

        // create request
        os.writeObject(reqType);
        os.flush();

        // wait for response
        return (Result) is.readObject();
    }



    private static void sendRequest(ObjectOutputStream os, RequestType reqType) {
        try {
            os.writeObject(reqType);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void keepTalking(ObjectOutputStream os) {
        sendRequest(os, new KeepTalkingRequest());
    }

    public static void sendMessages(ObjectOutputStream os, ArrayList<MsgModel> messages) {
        sendRequest(os, new MessageRequestType(messages));
    }

    public static void sendVoteNum(ObjectOutputStream os, int voteNum) {
        sendRequest(os, new SynchVoteNumRequest(voteNum));
    }

    public static void sendNotification(ObjectOutputStream os, ArrayList<String> peers) {
        sendRequest(os, new NotificationRequestType(peers));
    }

}
