package unicap.br.unimpact.domain.enums;

public enum StateEnum {
    DRAFT,
    PROPOSAL,
    PROJECT;

    public static StateEnum getNextState(StateEnum actualState) {
        return switch (actualState) {
            case DRAFT -> PROPOSAL;
            case PROPOSAL -> PROJECT;
            case PROJECT -> null;
        };
    }

    public static StateEnum getPreviousState(StateEnum actualState) {
        return switch (actualState) {
            case DRAFT -> null;
            case PROPOSAL -> DRAFT;
            case PROJECT -> PROPOSAL;
        };
    }
}
