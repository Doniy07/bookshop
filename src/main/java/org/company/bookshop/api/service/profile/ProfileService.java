package org.company.bookshop.api.service.profile;


import org.company.bookshop.api.dto.profile.ProfileRequestDTO;
import org.company.bookshop.api.dto.profile.ProfileRequestUpdateBalanceDTO;
import org.company.bookshop.api.dto.profile.ProfileRequestUpdateDTO;
import org.company.bookshop.api.dto.profile.ProfileResponseDTO;
import org.company.bookshop.api.util.ApiResponse;

import java.util.List;

public interface ProfileService {

    ApiResponse<List<ProfileResponseDTO>> findAll();

    ApiResponse<ProfileResponseDTO> findById(String profileId);

    ApiResponse<ProfileResponseDTO> save(ProfileRequestDTO request);

    ApiResponse<ProfileResponseDTO> update(String profileId, ProfileRequestDTO request);

    ApiResponse<ProfileResponseDTO> update(ProfileRequestUpdateDTO request);

    void updateBalance(String profileId, ProfileRequestUpdateBalanceDTO request);


    void delete(String profileId);

    ApiResponse<Double> viewBalance();
}