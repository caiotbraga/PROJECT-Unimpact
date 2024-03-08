package unicap.br.unimpact.api.dtos.response;

import lombok.Data;
import unicap.br.unimpact.domain.enums.MessageEnum;

import java.util.List;

@Data
public class MessageResponse {

    private String message;

    private Object object;

    public MessageResponse(MessageEnum messageEnum){
        this.message = messageEnum.getMessage();
    }

    public MessageResponse(MessageEnum messageEnum, Class<?> typeClass){
        this.message = String.format(messageEnum.getMessage(), typeClass.getSimpleName());
    }

    public MessageResponse(MessageEnum messageEnum, String value){
        this.message = String.format(messageEnum.getMessage(), value);
    }

    public MessageResponse(MessageEnum messageEnum, Object object){
        this.message = object instanceof List ? String.format(messageEnum.getMessage(), ((List<?>) object).get(0).getClass().getSimpleName()) :
                String.format(messageEnum.getMessage(), object.getClass().getSimpleName());
        this.object = object;
    }

    public MessageResponse(MessageEnum messageEnum, Class<?> typeClass, Object object){
        this.message = String.format(messageEnum.getMessage(), typeClass.getSimpleName());
        this.object = object;
    }

    public MessageResponse(MessageEnum messageEnum, Class<?> typeClass, String error){
        this.message = String.format(messageEnum.getMessage(), typeClass.getSimpleName()) + ": "+ error;
    }

    public MessageResponse(MessageEnum messageEnum, String value, String error){
        this.message = String.format(messageEnum.getMessage(), value) + ": "+ error;
    }

    public MessageResponse(String message, ApiErrorResponse error){
        this.message = message;
        this.object = error;
    }

}
