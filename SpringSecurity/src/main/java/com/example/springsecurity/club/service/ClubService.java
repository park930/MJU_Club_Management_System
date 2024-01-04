package com.example.springsecurity.club.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    public List<ClubDTO> findAll() {
        List<ClubEntity> clubEntityList = clubRepository.findAll();
        List<ClubDTO> clubDTOList = new ArrayList<>();
        for(ClubEntity clubEntity : clubEntityList){
            ClubDTO clubDTO = ClubDTO.toClubDTO(clubEntity);
            clubDTOList.add(clubDTO);
        }
        return clubDTOList;
    }


    public void save(ClubDTO clubDTO) {
        ClubEntity clubEntity = ClubEntity.toClubEntity(clubDTO);
        clubRepository.save(clubEntity);
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    public void updateClub(ClubDTO clubDTO) {
        ClubEntity clubEntity = ClubEntity.toUpdateClub(clubDTO);
        clubRepository.save(clubEntity);
    }

    public ClubDTO findById(Long id) {
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(id);
        if (optionalClubEntity.isPresent()){
            return ClubDTO.toClubDTO(optionalClubEntity.get());
        } else {
            return null;
        }
    }

    public ClubDTO findByCategory(String category) {
        ClubEntity findClubEntity = clubRepository.findByCategory(category);
        return ClubDTO.toClubDTO(findClubEntity);
    }
}
