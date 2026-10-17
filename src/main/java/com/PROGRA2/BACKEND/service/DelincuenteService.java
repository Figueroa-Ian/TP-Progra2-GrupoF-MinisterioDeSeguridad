
package com.PROGRA2.BACKEND.service;

import com.PROGRA2.BACKEND.dto.DelincuenteDTO;
import com.PROGRA2.BACKEND.mapper.DelincuenteMapper;
import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.repository.DelincuenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DelincuenteService {

    private final DelincuenteRepository repository;
    private final DelincuenteMapper mapper;

    // Equivalente a crearDelincuente(Delincuente)[cite: 7]
    public DelincuenteDTO crearDelincuente(DelincuenteDTO dto) {
        Delincuente entidad = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entidad));
    }

    // Equivalente a listarDelincuente()[cite: 7]
    public List<DelincuenteDTO> listarDelincuentes() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Equivalente a actualizarDelincuente(codigo, Delincuente)[cite: 7]
    public DelincuenteDTO actualizarDelincuente(Integer id, DelincuenteDTO dto) {
        return repository.findById(id).map(existente -> {
            existente.setCodigoDNI(dto.getCodigoDNI());
            existente.setNombre(dto.getNombre());
            existente.setApellido(dto.getApellido());
            existente.setDireccion(dto.getDireccion());
            return mapper.toDTO(repository.save(existente));
        }).orElseThrow(() -> new RuntimeException("Delincuente no encontrado"));
    }

    // Equivalente a eliminarDelincuente(codigo)[cite: 7]
    public void eliminarDelincuente(Integer id) {
        repository.deleteById(id);
    }
}
