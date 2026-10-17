
package com.PROGRA2.BACKEND.mapper;

import com.PROGRA2.BACKEND.dto.DelincuenteDTO;
import com.PROGRA2.BACKEND.models.Delincuente;
import com.tpfinal.backend.model.RolUsuario;
import org.springframework.stereotype.Component;

@Component
public class DelincuenteMapper {

    public DelincuenteDTO toDTO(Delincuente entity) {
        if (entity == null) return null;
        
        DelincuenteDTO dto = new DelincuenteDTO();
        dto.setId(entity.getId());
        dto.setCodigoDNI(entity.getCodigoDNI());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setDireccion(entity.getDireccion());
        if (entity.getRolUsuario() != null) {
            dto.setRolUsuario(entity.getRolUsuario().name());
        }
        return dto;
    }

    public Delincuente toEntity(DelincuenteDTO dto) {
        if (dto == null) return null;
        
        Delincuente entity = new Delincuente();
        entity.setId(dto.getId());
        entity.setCodigoDNI(dto.getCodigoDNI());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDireccion(dto.getDireccion());
        if (dto.getRolUsuario() != null) {
            entity.setRolUsuario(RolUsuario.valueOf(dto.getRolUsuario()));
        }
        return entity;
    }
}