package com.branciard.paza.pazachaingateway.service.mapper;

import com.branciard.paza.pazachaingateway.domain.*;
import com.branciard.paza.pazachaingateway.service.dto.ChainUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ChainUser and its DTO ChainUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChainUserMapper {

    ChainUserDTO chainUserToChainUserDTO(ChainUser chainUser);

    List<ChainUserDTO> chainUsersToChainUserDTOs(List<ChainUser> chainUsers);

    ChainUser chainUserDTOToChainUser(ChainUserDTO chainUserDTO);

    List<ChainUser> chainUserDTOsToChainUsers(List<ChainUserDTO> chainUserDTOs);
}
