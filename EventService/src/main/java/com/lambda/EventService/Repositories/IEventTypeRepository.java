package com.lambda.EventService.Repositories;

import com.lambda.EventService.ExceptionHandling.CustomEventException;
import com.lambda.EventService.Models.Entity.EventType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEventTypeRepository extends CrudRepository<EventType, Long> {
    EventType findById(long id)throws CustomEventException;
    List<EventType> findByEventTypeDescription(String desc) throws CustomEventException;
}
