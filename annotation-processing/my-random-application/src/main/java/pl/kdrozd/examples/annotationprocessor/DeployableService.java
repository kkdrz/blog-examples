package pl.kdrozd.examples.annotationprocessor;

@Handler
public abstract class DeployableService {

    public void onMessage(Message message) {
        // process the message
    }

    @SessionField
    public abstract void setUserId(String id);

    @SessionField
    public abstract void setSomething(String id);

    @SessionField
    public abstract void setTimestamp(String id);

    public abstract String getUserId();

}
