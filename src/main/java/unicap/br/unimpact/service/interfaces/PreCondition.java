package unicap.br.unimpact.service.interfaces;

import unicap.br.unimpact.domain.entities.BaseEntity;


public interface PreCondition<T extends BaseEntity> {

    void preRegister(T entity);
    void preEdit(T entity);
    void preDelete(T entity);
}
