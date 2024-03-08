package unicap.br.unimpact.domain.enums;

public enum NotificationMessageEnum {

    REMOVAL_REPRESENTATIVE("A organização %s lhe removeu da lista de representantes!"),
    REMOVAL_GROUP("%s lhe removeu do grupo %s!"),
    LEAVE_GROUP("%s saiu do grupo %s!"),
    INVITE_REPRESENTATIVE("A organização %s lhe convidou para se tornar um de seus representantes!"),
    INVITE_GROUP("%s lhe convidou para participar do grupo %s!"),
    ACCEPT_INVITE_REPRESENTATIVE("%s aceitou o convite para se tornar um representante!"),
    DECLINE_INVITE_REPRESENTATIVE("%s recusou o convite para se tornar um representante!"),
    ACCEPT_INVITE_GROUP("%s aceitou o convite para participar do grupo %s!"),
    DECLINE_INVITE_GROUP("%s recusou o convite para participar do grupo %s!");

    private final String message;

    NotificationMessageEnum(String message) {
        this.message = message;
    }

    public static String getMessage(NotificationMessageEnum notificationMessage) {
        return notificationMessage.message;
    }
}
