package unicap.br.unimpact.domain.enums;

public enum MessageEnum {
    REGISTERED("%s registered successfully!"),
    EDITED("Successfully edited %s!"),
    FOUND("Found %s!"),
    NOT_FOUND("%s not found!"),
    EMPTY("%s is empty!"),
    DELETED("%s successfully deleted!"),
    ALREADY_USED("%s is already being used!"),
    ADDED("%s successfully added"),
    SENT("%s successfully sent"),
    ACCEPTED("%s successfully accept"),
    DECLINED("%s successfully declined"),
    ACTION_TAKEN("the action was carried out successfully!");

    private final String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
