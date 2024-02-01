package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.score.repository.ScoreRepository;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.dto.TodoPersonalDTO;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoClubRepository;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.time.Duration;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoClubRepository todoClubRepository;
    private final ClubRepository clubRepository;
    private final TodoCommentRepository todoCommentRepository;

    public List<TodoDTO> findAll() {
        List<TodoEntity> todoEntityList = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for(TodoEntity todoEntity : todoEntityList){
            TodoDTO todoDTO = TodoDTO.toTodoDTO(todoEntity);
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }

    public TodoEntity saveTodo(TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoEntity.toTodoEntity(todoDTO);
        TodoEntity saved = todoRepository.save(todoEntity);
        return saved;
    }


    public void saveTodoClub(ClubEntity clubEntity, TodoEntity savedTodo) {
        todoClubRepository.save(TodoClubEntity.newTodoClub(clubEntity,savedTodo));
    }

    public TodoDTO findById(Long id) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);
        if (optionalTodoEntity.isPresent()){
            TodoEntity todoEntity = optionalTodoEntity.get();
            return TodoDTO.toTodoDTO(todoEntity);
        } else {
            return null;
        }
    }

    public Long flipChecked(TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoEntity.toFlipChecked(todoDTO);
        return todoRepository.save(todoEntity).getId();
    }


    public List<TodoDTO> findAllByUserWriter(String userId) {
        List<TodoEntity> todoEntityList = todoRepository.findAllByWriterOrderByEndTimeDesc(userId);
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for(TodoEntity todoEntity : todoEntityList){
            TodoDTO todoDTO = TodoDTO.toTodoDTO(todoEntity);
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }

    public TodoPersonalDTO getFilteredTodoList(ClubDTO clubDTO,String userName) {
        TodoPersonalDTO todoPersonalDTO = new TodoPersonalDTO();
        List<TodoClubEntity> todoClubList = todoClubRepository.findAllByClubEntity(ClubEntity.toUpdateClub(clubDTO));
        List<TodoDTO> totalTodoDTOList = new ArrayList<>();
        List<TodoDTO> completeTodoList = new ArrayList<>();
        List<TodoDTO> incompleteTodoList = new ArrayList<>();
        List<TodoDTO> myTodoList = new ArrayList<>();
        List<String> remainTimeList = new ArrayList<>();
        List<LocalDateTime> submitDateList = new ArrayList<>();

        myTodoList = findAllByUserWriter(userName);

        for(TodoClubEntity todoClubEntity : todoClubList){
            TodoEntity todoEntity = todoClubEntity.getTodoEntity();
            TodoDTO todoDTO = TodoDTO.toTodoDTO(todoEntity);
            totalTodoDTOList.add(todoDTO);
            if (todoClubEntity.getResultSubmit()==1){
                TodoCommentEntity todoCommentEntity = todoCommentRepository.findByTodoEntityAndClubEntityAndIsSubmit(todoEntity, ClubEntity.toUpdateClub(clubDTO),1);
                submitDateList.add(todoCommentEntity.getCreatedTime());
                completeTodoList.add(todoDTO);
            } else if (todoDTO.getWriter().equals(userName)) {
                continue;
            } else {

                LocalDateTime endTime = todoDTO.getEndTime();
                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(currentTime, endTime);

                // 남은 시간을 일(day)과 시간으로 변환하여 문자열로 저장
                long days = duration.toDays();
                long hours = duration.toHours() % 24;
                String remainTime = days + "일 " + hours + "시간";
                remainTimeList.add(remainTime);
                incompleteTodoList.add(todoDTO);
            }
        }
        todoPersonalDTO.setTotalTodoDTOList(totalTodoDTOList);
        todoPersonalDTO.setReceviedCompleteList(completeTodoList);
        todoPersonalDTO.setReceviedIncompleteList(incompleteTodoList);
        todoPersonalDTO.setSubmitDateList(submitDateList);
        todoPersonalDTO.setMyTodoDTOList(myTodoList);
        todoPersonalDTO.setRemainTimeList(remainTimeList);
        return todoPersonalDTO;
    }

    @Transactional
    public List<TodoCommentDTO> filterCompleteClub(TodoDTO todoDTO) {
        if (todoDTO != null){

            List<TodoCommentEntity> todoCommentEntityList = todoCommentRepository.findAllByTodoEntityAndIsSubmitOrderByCreatedTimeDesc(TodoEntity.toUpdateTodoEntity(todoDTO), 1);
            List<TodoCommentDTO> todoCommentDTOList = new ArrayList<>();
            for(TodoCommentEntity todoCommentEntity : todoCommentEntityList){
                TodoCommentDTO todoCommentDTO = TodoCommentDTO.toTodoCommentDTO(todoCommentEntity);
                Long clubId = todoCommentDTO.getClubId();

                Optional<ClubEntity> optionalClubEntity = clubRepository.findById(clubId);
                if (optionalClubEntity.isPresent()) {
                    todoCommentDTO.setClubName(optionalClubEntity.get().getClubName());
                    todoCommentDTOList.add(todoCommentDTO);
                }

            }
            return todoCommentDTOList;
        } else {
            return null;
        }
    }

    public List<ClubDTO> getImcompleteClubList(TodoDTO todoDTO,List<TodoCommentDTO> completeCommentList) {
        if (todoDTO != null){
            List<TodoClubEntity> todoEntityList = todoClubRepository.findAllByTodoEntity(TodoEntity.toUpdateTodoEntity(todoDTO));
            List<ClubDTO> clubDTOList = new ArrayList<>();
            List<Long> completeId = new ArrayList<>();
            for(TodoCommentDTO todoCommentDTO : completeCommentList){
                completeId.add(todoCommentDTO.getClubId());
            }

            for (TodoClubEntity todoClubEntity : todoEntityList) {
                Optional<ClubEntity> optionalClubEntity = clubRepository.findById(todoClubEntity.getClubEntity().getId());
                if (optionalClubEntity.isPresent()){
                    ClubEntity clubEntity = optionalClubEntity.get();
                    if (!completeId.contains(clubEntity.getId())) {
                        clubDTOList.add(ClubDTO.toClubDTO(clubEntity));
                    }
                }
            }
            return clubDTOList;
        } else {
            return null;
        }
    }

    public List<TodoDTO> findAllByAdminWriter() {
        List<TodoEntity> todoEntityList = todoRepository.findAllByWriterStartingWithOrderByEndTimeDesc("admin");
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for( TodoEntity todoEntity : todoEntityList){
            todoDTOList.add(TodoDTO.toTodoDTO(todoEntity));
        }
        return todoDTOList;
    }

    public List<TodoDTO> filterReceivedTodo(List<TodoDTO> totalTodoList) {
        List<TodoDTO> recivedTodoList = new ArrayList<>();
        for(TodoDTO todoDTO : totalTodoList){
            if (todoDTO.getWriter().startsWith("admin")){
                recivedTodoList.add(todoDTO);
            }
        }
        return recivedTodoList;
    }

    public List<TodoDTO> filterMyTodo(List<TodoDTO> totalTodoList) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TodoDTO> myTodoList = new ArrayList<>();
        for(TodoDTO todoDTO : totalTodoList){
            if (todoDTO.getWriter().equals(id)){
                myTodoList.add(todoDTO);
            }
        }
        return myTodoList;
    }

    public List<Map<String, Object>> getEventList(TodoPersonalDTO todoPersonalDTO) {
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        Map<String, Object> event = null;
        for ( TodoDTO todoDTO : todoPersonalDTO.getReceviedIncompleteList()){
            event = new HashMap<String, Object>();
            event.put("start", todoDTO.getStartTime());
            event.put("title", todoDTO.getTitle());
            event.put("end", todoDTO.getEndTime());
            event.put("color","#FF0000");
            eventList.add(event);
        }

        for ( TodoDTO todoDTO : todoPersonalDTO.getMyTodoDTOList()){
            event = new HashMap<String, Object>();
            event.put("start", todoDTO.getStartTime());
            event.put("title", todoDTO.getTitle());
            event.put("end", todoDTO.getEndTime());
            event.put("color","#58ACFA");
            eventList.add(event);
        }

        System.out.println("eventList = " + eventList);
        return eventList;
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void update(TodoDTO todoDTO) {
        todoRepository.save(TodoEntity.toUpdateTodoEntity(todoDTO));
    }

}
