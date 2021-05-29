package pl.kdrozd.examples.annotationprocessor;

@Handler
public abstract class AnotherService {

    public void onMessage(Message message) {
        // process the message
    }

    @SessionField
    public abstract void setSessionId(String id);

    public abstract String getSessionId();

}
