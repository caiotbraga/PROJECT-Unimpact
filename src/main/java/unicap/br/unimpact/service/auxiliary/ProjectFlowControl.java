package unicap.br.unimpact.service.auxiliary;

import lombok.Data;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;

import java.util.List;

@Data
public class ProjectFlowControl {

    private StateEnum actualState;

    private StatusEnum actualStatus;

    private StateEnum nextState;

    private List<StatusEnum> nextStatus;

    private FunctionEnum authorizedUser;

    public ProjectFlowControl(StateEnum actualState, StatusEnum actualStatus) {
        this.actualState = actualState;
        this.actualStatus = actualStatus;
        this.setNextStatusAndState();
    }

    public ProjectFlowControl() {
        this.actualState = StateEnum.DRAFT;
        this.actualStatus = StatusEnum.IN_ELABORATION;
    }


    public void setNextStatusAndState() {
        if (this.actualState.equals(StateEnum.DRAFT)) {
            switch (this.actualStatus) {
                case IN_ELABORATION -> {
                    this.nextState = this.actualState;
                    this.nextStatus = List.of(StatusEnum.IN_REVIEW);
                }

                case ADJUSTABLE -> {
                    this.nextState = this.actualState;
                    this.nextStatus = List.of(StatusEnum.IN_ELABORATION);
                }

                case IN_REVIEW -> {
                    this.nextState = this.actualState;
                    this.nextStatus = List.of(StatusEnum.ACCEPT,
                            StatusEnum.ADJUSTABLE, StatusEnum.RECUSED);
                }

                case RECUSED -> {
                    this.nextState = this.actualState;
                    this.nextStatus = List.of();
                }

                case ACCEPT -> {
                    this.nextState = StateEnum.getNextState(actualState);
                    this.nextStatus = List.of(StatusEnum.IN_REVIEW);
                }

                default -> throw new IllegalStateException("Unexpected value: " + actualStatus);
            }

        } else if (actualState.equals(StateEnum.PROPOSAL)) {
            switch (actualStatus) {
                case IN_REVIEW -> {
                    this.nextState = this.actualState;
                    this.nextStatus = List.of(StatusEnum.ACCEPT, StatusEnum.RECUSED);
                }

                case RECUSED_PLAN, RECUSED -> {
                    this.nextState = StateEnum.getPreviousState(actualState);
                    this.nextStatus = List.of(StatusEnum.IN_REVIEW);
                }

                case ACCEPT, ADJUSTABLE_PLAN -> {
                    this.nextState = actualState;
                    this.nextStatus = List.of(StatusEnum.DEVELOPMENT_PLAN);
                }

                case DEVELOPMENT_PLAN -> {
                    this.nextState = actualState;
                    this.nextStatus = List.of(StatusEnum.PLAN_REVIEW);
                }

                case PLAN_REVIEW -> {
                    this.nextState = actualState;
                    this.nextStatus = List.of(StatusEnum.ACCEPT_PLAN,
                            StatusEnum.ADJUSTABLE_PLAN, StatusEnum.RECUSED_PLAN);
                }

                case ACCEPT_PLAN -> {
                    this.nextState = StateEnum.getNextState(actualState);
                    this.nextStatus = List.of(StatusEnum.IN_RUN);
                }

                default -> throw new IllegalStateException("Unexpected value: " + actualStatus);
            }
        } else if (actualState.equals(StateEnum.PROJECT)) {
            if (actualStatus == StatusEnum.IN_RUN) {
                this.nextState = actualState;
                this.nextStatus = List.of(StatusEnum.FINISHED);
            } else {
                throw new IllegalStateException("Unexpected value: " + actualStatus);
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + actualState);
        }
    }


    public List<FunctionEnum> getAuthorizedUsers() {
        if (this.nextState.equals(StateEnum.DRAFT)) {
            List<FunctionEnum> responsible = List.of(FunctionEnum.ROLE_MIDDLE_EMPLOYEE);
            List<FunctionEnum> client = List.of(FunctionEnum.ROLE_REQUESTING_SIMPLE, FunctionEnum.ROLE_REQUESTING_EMPLOYEE);

            return switch (this.nextStatus.get(0)) {
                case IN_REVIEW, IN_ELABORATION -> client;
                case ACCEPT, ADJUSTABLE, RECUSED -> responsible;
                default -> null;
            };

        } else if (this.nextState.equals(StateEnum.PROPOSAL)) {
            List<FunctionEnum> responsible = List.of(FunctionEnum.ROLE_EXECUTING_EMPLOYEE);
            List<FunctionEnum> client = List.of(FunctionEnum.ROLE_REQUESTING_SIMPLE, FunctionEnum.ROLE_REQUESTING_EMPLOYEE);

            return switch (this.nextStatus.get(0)) {
                case IN_REVIEW, ACCEPT, RECUSED, DEVELOPMENT_PLAN, PLAN_REVIEW -> responsible;
                case ACCEPT_PLAN, ADJUSTABLE_PLAN, RECUSED_PLAN -> client;
                default -> null;
            };

        } else if (this.nextState.equals(StateEnum.PROJECT)) {
            List<FunctionEnum> responsible = List.of(FunctionEnum.ROLE_EXECUTING_EMPLOYEE);

            return switch (this.nextStatus.get(0)) {
                case IN_RUN, FINISHED -> responsible;
                default -> null;
            };
        } else {
            return null;
        }
    }

}
