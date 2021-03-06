package com.lambda.EventService.Services.ServiceImplementation;

import com.lambda.EventService.ExceptionHandling.CustomEventException;
import com.lambda.EventService.Models.Entity.Event;
import com.lambda.EventService.Services.IEventTypeService;
import com.lambda.EventService.Models.Entity.EventType;
import com.lambda.EventService.Repositories.IEventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventTypeService implements IEventTypeService {

    @Autowired
    IEventTypeRepository eventTypeRepository;



    @Override
    public EventType createEventType(EventType object)throws CustomEventException {
        if(object == null || object.getEventTypeDescription()== null) throw new CustomEventException("400: One or more attributes of class EventType (possibly whole object) is null!");
        var eventTypeRepositoryTemp = eventTypeRepository.save(object);
        return object;
    }
    @Override
    public List<EventType> findByEventTypeDescription(String desc) throws CustomEventException{
        if(desc == null || desc == "") throw new CustomEventException("400: Description of EventType is either null or an empty string.");
        var eventTypeRepositoryTemp = eventTypeRepository.findByEventTypeDescription(desc);
        return eventTypeRepositoryTemp;
    }
    @Override
    public EventType findById(Long id) throws CustomEventException {
        long idd = id;
        var result = eventTypeRepository.findById(idd);
        if(result != null) return result;
        throw new CustomEventException("404: The EventType with ID="+id.toString()+" does not exist in the database.");
    }

    @Override
    public EventType updateEventType(EventType updatedValue)throws CustomEventException{
        if(updatedValue == null || updatedValue.getEventTypeDescription()== null)
            throw new CustomEventException("400: One or more attributes of class EventType (possibly whole object) is null or EventList is empty!");
        if(updatedValue.getEventList() == null) updatedValue.setEventList(new ArrayList<Event>());
        return eventTypeRepository.save(updatedValue);
    }
}
