package unicap.br.unimpact.service.services.others;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.domain.enums.*;


@Service
@Slf4j
@AllArgsConstructor
public class AuxiliaryComponentsService {

    public ResponseEntity<?> getAllInstitutionType() {
        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND, InstitutionTypeEnum.values()));
    }


    public ResponseEntity<?> getAllNotificationType() {
        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND, NotificationTypeEnum.values()));
    }


    public ResponseEntity<?> getAllFunctionUserType() {
        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND, FunctionEnum.values()));
    }


    public ResponseEntity<?> getAllNotificationStatus() {
        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND, NotificationStatusEnum.values()));
    }

}
