package unicap.br.unimpact.service.interfaces;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.response.DTOResponse;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.domain.entities.BaseEntity;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.auxiliary.EntityValidator;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public abstract class GenericCRUD<T extends BaseEntity, D extends DTOResponse> {

    private Class<T> entityClass;
    private Class<D> dtoClass;
    private JpaRepository<T, Long> baseRepository;
    private PreCondition<T> preCondition;

    private ModelMapper mapper;

    public void init(Class<T> entityClass, Class<D> dtoClass,
                     JpaRepository<T, Long> baseRepository,
                     PreCondition<T> preCondition) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.baseRepository = baseRepository;
        this.preCondition = preCondition;
        this.mapper = new ModelMapper();
    }


    @Transactional
    public T register(Object object) {
        T entity = this.getInstanceOfEntity();
        mapper.map(object, entity);

        if (!Objects.isNull(preCondition)) preCondition.preRegister(entity);
        EntityValidator.validate(entity);

        this.baseRepository.save(entity);

        return entity;
    }

    @Transactional
    public T edit(Long id, Object object) {
        T entity = get(id);

        this.mapper.map(object, entity);
        if (!Objects.isNull(preCondition)) preCondition.preEdit(entity);
        this.baseRepository.save(entity);

        return entity;
    }

    @Transactional
    public T get(Long id) {
        Optional<T> entity = this.baseRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new NotFoundException(this.entityClass);
        }
    }

    @Transactional
    public List<T> getAll() {
        return this.baseRepository.findAll();
    }


    private List<D> getAllDTOs(List<T> entities) {
        if (!entities.isEmpty()) {
            return entities.stream().map(e -> {
                D dto = this.getInstanceOfDTO();
                this.mapper.map(e, dto);
                return dto;
            }).collect(Collectors.toList());

        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public ResponseEntity<?> getResponseByEntity(T entity, MessageEnum messageEnum) {
        D dtoResponse = this.getInstanceOfDTO();
        this.mapper.map(entity, dtoResponse);
        return ResponseEntity.ok(new MessageResponse(messageEnum,
                entityClass, dtoResponse));
    }


    @Transactional
    public ResponseEntity<?> getResponseByList(List<T> entities, MessageEnum messageEnum) {
        List<D> dtos = this.getAllDTOs(entities);

        return ResponseEntity.ok(new MessageResponse(messageEnum,
                entityClass, dtos));
    }


    public ResponseEntity<?> getResponseSimple(MessageEnum messageEnum) {
        return ResponseEntity.ok(new MessageResponse(messageEnum, entityClass));
    }


    @Transactional
    public T delete(Long id) {
        T entity = this.get(id);
        D dtoResponse = this.getInstanceOfDTO();
        this.mapper.map(entity, dtoResponse);

        if (!Objects.isNull(preCondition)) preCondition.preDelete(entity);
        this.baseRepository.delete(entity);

        return entity;
    }

    private T getInstanceOfEntity() {
        try {
            return this.entityClass.getDeclaredConstructor().newInstance();

        } catch (InstantiationException | InvocationTargetException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private D getInstanceOfDTO() {
        try {
            return this.dtoClass.getDeclaredConstructor().newInstance();

        } catch (InstantiationException | InvocationTargetException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public ModelMapper getMapper() {
        return this.mapper;
    }
}
