package com.example.springsecurity.score.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.score.dto.ClubRatingDTO;
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
        if (scoreEntity == null){
            return null;
        }
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

        if(scoreDTOList.isEmpty()){
            return null;
        }

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
        if (updateScoreList == null){
            return null;
        }

        List<String> headList = new ArrayList<>();
        int num = updateScoreList.size();
        for(int i=0;i<num;i++){
            headList.add("제출여부");
            headList.add("가산점");
        }
        return headList;
    }

    public List<Integer> getTotalScore(List<ScoreDTO> updateScoreList) {

        if (updateScoreList == null){
            return null;
        }

        List<Integer> totalScoreList = new ArrayList<>();
        for(int i=0;i<updateScoreList.get(0).getTotalScoreList().size();i++){
            totalScoreList.add(0);
        }

        for(ScoreDTO scoreDTO : updateScoreList){
            int index=0;
            for(int score : scoreDTO.getTotalScoreList()){
                int num = totalScoreList.get(index);
                totalScoreList.set(index,num+score);
                index++;
            }
            index=0;
            for(int plusScore : scoreDTO.getTotalPlusScoreList()){
                int num = totalScoreList.get(index);
                totalScoreList.set(index,num+plusScore);
                index++;
            }
        }
        return totalScoreList;
    }


    public ClubRatingDTO sortScore(List<String> headText, List<Integer> totalScoreList, List<ScoreDTO> updateScoreList, List<ClubDTO> clubDTOList) {
        List<Integer> order = new ArrayList<>();
        if (totalScoreList == null){
            return null;
        }

        for (int i = 0; i < totalScoreList.size(); i++) {
            order.add(i);
        }
        
        //총합 내림차순 기준으로 재정렬
        Collections.sort(order, Comparator.comparingInt(totalScoreList::get).reversed());
        Collections.sort(totalScoreList, Comparator.reverseOrder());

        //동아리 재정렬
        List<ClubDTO> sortedClubList = new ArrayList<>(Collections.nCopies(clubDTOList.size(), null));
        for (int i = 0; i < order.size(); i++) {
            sortedClubList.set(i, clubDTOList.get(order.get(i)));
        }

        //세부 점수들 재정렬
        for(ScoreDTO scoreDTO : updateScoreList){
            List<Integer> scoreList = scoreDTO.getTotalScoreList();
            List<Integer> plusScoreList = scoreDTO.getTotalPlusScoreList();
            List<Integer> sortedScoreList = new ArrayList<>(Collections.nCopies(scoreList.size(),0));
            List<Integer> sortedPlusScoreList = new ArrayList<>(Collections.nCopies(plusScoreList.size(),0));
            for (int i = 0; i < order.size(); i++) {
                sortedScoreList.set(i, scoreList.get(order.get(i)));
                sortedPlusScoreList.set(i, plusScoreList.get(order.get(i)));
            }
            scoreDTO.setTotalScoreList(sortedScoreList);
            scoreDTO.setTotalPlusScoreList(sortedPlusScoreList);
        }

        ClubRatingDTO clubRatingDTO = new ClubRatingDTO(headText,totalScoreList,updateScoreList,sortedClubList);
        System.out.println("clubRatingDTO = " + clubRatingDTO);
        return clubRatingDTO;
    }

    public ScoreDTO findById(Long scoreId) {
        Optional<ScoreEntity> optionalScoreEntity = scoreRepository.findById(scoreId);
        if (optionalScoreEntity.isPresent()){
            ScoreEntity scoreEntity = optionalScoreEntity.get();
            return ScoreDTO.toScoreDTO(scoreEntity);
        } else {
            return null;
        }
    }


    public void delete(ScoreDTO scoreDTO) {
        scoreRepository.delete(ScoreEntity.toUpdateScoreEntity(scoreDTO));
    }

    public int getMyClubScore(ClubRatingDTO clubRatingDTO, ClubDTO myClubDTO) {
        int index=0;

        if (clubRatingDTO == null){
            return 0;
        }

        for(ClubDTO clubDTO : clubRatingDTO.getClubDTOList()){
            if (clubDTO.getId().equals(myClubDTO.getId())){
                break;
            }
            index++;
        }
        return clubRatingDTO.getTotalScoreList().get(index);
    }

    public Long update(ScoreDTO scoreDTO) {
        ScoreEntity scoreEntity = ScoreEntity.toUpdateScoreEntity(scoreDTO);
        return scoreRepository.save(scoreEntity).getId();
    }
}
