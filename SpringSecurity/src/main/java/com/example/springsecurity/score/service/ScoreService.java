package com.example.springsecurity.score.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.score.dto.ScoreClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.score.entity.ScoreEntity;
import com.example.springsecurity.score.repository.ScoreClubRepository;
import com.example.springsecurity.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreDTO saveScoreTable(ScoreDTO scoreDTO) {
        ScoreEntity scoreEntity = ScoreEntity.toNewScoreEntity(scoreDTO);
        ScoreEntity savedScoreEntity = scoreRepository.save(scoreEntity);
        return ScoreDTO.toScoreDTO(savedScoreEntity);
    }

    public ScoreDTO findByTodoId(Long id) {
        ScoreEntity scoreEntity = scoreRepository.findByTodoId(id);
        return ScoreDTO.toScoreDTO(scoreEntity);
    }

    public List<ScoreDTO> findAll() {
        List<ScoreEntity> scoreEntityList = scoreRepository.findAll();
        List<ScoreDTO> scoreDTOList = new ArrayList<>();
        for(ScoreEntity scoreEntity : scoreEntityList){
            ScoreDTO scoreDTO = ScoreDTO.toScoreDTO(scoreEntity);
            scoreDTOList.add(scoreDTO);
        }
        return scoreDTOList;
    }

    public List<ScoreDTO> getScoreInfo(List<ScoreClubDTO> scoreClubDTOList, List<ScoreDTO> scoreDTOList, List<ClubDTO> clubDTOList) {
        Long firstScoreId = scoreClubDTOList.get(0).getScoreId();

        int index = 0;
        int lastIndex = clubDTOList.size() - 1;
        Long lastClubId = clubDTOList.get(lastIndex).getId();
        List<Integer> tempList =  resetTempScoreList(lastClubId);
        List<Integer> tempList2 =  resetTempScoreList(lastClubId);
        //점수를 저장할 DTO
        ScoreDTO scoreDTO = findScoreDTO(firstScoreId, scoreDTOList);
        for (ScoreClubDTO scoreClubDTO : scoreClubDTOList) {
            index++;
            Long nowScoreId = scoreClubDTO.getScoreId();
            if (!nowScoreId.equals(firstScoreId)) {
                //저장해왔던거, set으로 넣어줌
                scoreDTO.setTotalScoreList(arrangeScoreList(clubDTOList,tempList));
                scoreDTO.setTotalPlusScoreList(arrangeScoreList(clubDTOList,tempList2));

                //다시 초기화
                tempList = resetTempScoreList(lastClubId);
                tempList2 = resetTempScoreList(lastClubId);
                scoreDTO = findScoreDTO(nowScoreId,scoreDTOList);
                firstScoreId = nowScoreId;
            }
                Long indexId = scoreClubDTO.getClubId();
            tempList.set(Math.toIntExact(indexId),scoreClubDTO.getSubmitType());
            tempList2.set(Math.toIntExact(indexId),scoreClubDTO.getPlusScoreType());

            if (index == scoreClubDTOList.size()){
                scoreDTO.setTotalScoreList(arrangeScoreList(clubDTOList,tempList));
                scoreDTO.setTotalPlusScoreList(arrangeScoreList(clubDTOList,tempList2));
            }
        }
        return scoreDTOList;

    }

    private List<Integer> arrangeScoreList(List<ClubDTO> clubDTOList, List<Integer> tempList) {
        List<Integer> finalScoreList = new ArrayList<>();
        for(ClubDTO clubDTO : clubDTOList){
            int tempNum = tempList.get(Math.toIntExact(clubDTO.getId()));
            finalScoreList.add(tempNum);
        }
        return finalScoreList;
    }

    private List<Integer> resetTempScoreList(Long clubNum) {
        List<Integer> reset = new ArrayList<>();
        for(int i=0;i<=clubNum;i++){
            reset.add(0);
        }
        return reset;
    }

    private ScoreDTO findScoreDTO(Long scoreId, List<ScoreDTO> scoreDTOList) {
        for(ScoreDTO scoreDTO : scoreDTOList){
            if (Objects.equals(scoreId, scoreDTO.getId())){
                return scoreDTO;
            }
        }
        return null;
    }

    public List<String> setHeadText(List<ScoreDTO> updateScoreList) {
        List<String> headList = new ArrayList<>();
        int num = updateScoreList.size();
        for(int i=0;i<num;i++){
            headList.add("제출여부");
            headList.add("가산점");
        }
        return headList;
    }
}
