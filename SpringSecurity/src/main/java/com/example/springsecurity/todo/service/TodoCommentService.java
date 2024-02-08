package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoCommentFileEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoCommentFileRepository;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoCommentService {
    private final TodoCommentRepository todoCommentRepository;
    private final TodoRepository todoRepository;
    private final TodoCommentFileRepository todoCommentFileRepository;
    private final ClubRepository clubRepository;


    @Transactional
    public LocalDateTime save(TodoCommentDTO todoCommentDTO, TodoDTO todoDTO, ClubDTO clubDTO) throws IOException {

        TodoCommentEntity saved = null;
        if (todoCommentDTO.getResultFile().isEmpty()){
            TodoCommentEntity todoCommentEntity = TodoCommentEntity.toSaveTodoCommentEntity(todoCommentDTO,TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
            saved = todoCommentRepository.save(todoCommentEntity);
        } else {
            MultipartFile resultFile = todoCommentDTO.getResultFile();
            String originalFilename = resultFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/joohyun/Desktop/login_project/login_project/SpringSecurity/src/main/resources/static/fileFolder/" + storedFileName;
            resultFile.transferTo(new File(savePath));
            TodoCommentEntity todoCommentEntity = TodoCommentEntity.toSaveFileTodoCommentEntity(todoCommentDTO,TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
            Long id = todoCommentRepository.save(todoCommentEntity).getId();
            saved = todoCommentRepository.findById(id).get();

            TodoCommentFileEntity todoCommentFileEntity = TodoCommentFileEntity.toTodoCommentFileEntity(saved, originalFilename, storedFileName);
            todoCommentFileRepository.save(todoCommentFileEntity);
        }

        return saved.getCreatedTime();
    }

    @Transactional
    public List<TodoCommentDTO> findAll(TodoDTO todoDTO, ClubDTO clubDTO) {
        List<TodoCommentEntity> commentEntityList = todoCommentRepository.findAllByTodoEntityAndClubEntityOrderByCreatedTimeDesc(TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
        List<TodoCommentDTO> todoCommentDTOList = new ArrayList<>();
        for (TodoCommentEntity todoCommentEntity : commentEntityList){
            TodoCommentDTO todoCommentDTO = TodoCommentDTO.toTodoCommentDTO(todoCommentEntity);
            todoCommentDTOList.add(todoCommentDTO);
        }
        return todoCommentDTOList;
    }

    @Transactional
    public TodoCommentDTO findById(Long commentId) {
        Optional<TodoCommentEntity> optionalTodoCommentEntity = todoCommentRepository.findById(commentId);
        if (optionalTodoCommentEntity.isPresent()){
            return  TodoCommentDTO.toTodoCommentDTO(optionalTodoCommentEntity.get());
        } else {
            return null;
        }
    }


    @Transactional
    public Long updateComment(TodoCommentDTO todoCommentDTO, String deleteFileId) throws IOException {
        Optional<TodoCommentEntity> optionalTodoCommentEntity = todoCommentRepository.findById(todoCommentDTO.getId());
        if (optionalTodoCommentEntity.isPresent()){
            TodoCommentEntity todoCommentEntity = optionalTodoCommentEntity.get();

            if (todoCommentDTO.getResultFile().isEmpty()){
                System.out.println("파일 선택 안했을 때");
                if (deleteFileId != null) {
                    System.out.println("1.새로운 파일 선택안하고 기존파일 삭제한 경우");
                    // 1.새로운 파일 선택안하고 기존파일 삭제한 경우
                    todoCommentEntity.setFileAttached(0);
                    todoCommentFileRepository.delete(todoCommentFileRepository.findByStoredFileName(deleteFileId));

                }

            } else if (!todoCommentDTO.getResultFile().isEmpty()){

                // (해당 파일을 특정 경로에 저장하고 DB에 저장할 이름들을 설정하는 과정)
                MultipartFile resultFile = todoCommentDTO.getResultFile();
                String originalFilename = resultFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = "C:/Users/joohyun/Desktop/login_project/login_project/SpringSecurity/src/main/resources/static/fileFolder/" + storedFileName;
                resultFile.transferTo(new File(savePath));

                TodoCommentFileEntity fileEntity = null;
                if (todoCommentEntity.getFileAttached() == 1){

                    System.out.println("3. 새로운 파일 선택하고, 기존에는 다른 파일이 있는 경우");
                    // 3. 새로운 파일 선택하고, 기존에는 다른 파일이 있는 경우 --> 기존파일 정보를 새로운 파일로 변경해야함
                    fileEntity = todoCommentFileRepository.findByTodoCommentEntity(todoCommentEntity);
                    fileEntity.setOriginalFileName(originalFilename);
                    fileEntity.setStoredFileName(storedFileName);

                } else {

                    System.out.println("4. 새로운 파일 선택했지만, 기존에는 파일 선택 안한 경우");
                    // 4. 새로운 파일 선택했지만, 기존에는 파일 선택 안한 경우
                    fileEntity = TodoCommentFileEntity.toTodoCommentFileEntity(todoCommentEntity, originalFilename, storedFileName);
                    todoCommentEntity.setFileAttached(1);
                }

                todoCommentFileRepository.save(fileEntity);



            }

            System.out.println("내용 변경 적용");
            todoCommentEntity.setContent(todoCommentDTO.getContent());

            //comment엔티티 저장해야함
            return todoCommentRepository.save(todoCommentEntity).getId();

        } else {
            return null;
        }
    }
}
