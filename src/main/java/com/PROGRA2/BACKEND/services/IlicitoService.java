package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Ilicito;
import com.PROGRA2.BACKEND.models.Delito;
import com.PROGRA2.BACKEND.repository.ExpedienteRepository;
import com.PROGRA2.BACKEND.repository.DelitoRepository;
import com.PROGRA2.BACKEND.repository.IlicitoRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IlicitoService implements IIlicitoService {

    private final IlicitoRepository ilicitoRepository;
    private final ExpedienteRepository expedienteRepository;
    private final DelitoRepository delitoRepository;

    public IlicitoService(IlicitoRepository ilicitoRepository, ExpedienteRepository expedienteRepository,
                          DelitoRepository delitoRepository) {
        this.ilicitoRepository = ilicitoRepository;
        this.expedienteRepository = expedienteRepository;
        this.delitoRepository = delitoRepository;
    }

    @Override
    public List<Ilicito> traerTodosLosIlicitos() {
        return ilicitoRepository.findAll();
    }

    @Override
    public Ilicito traerIlicitoPorId(Integer id) {
        return ilicitoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un ilícito con id " + id));
    }

    @Override
    @Transactional
    public Ilicito guardarIlicito(Ilicito ilicito) {
        ilicito.setId(null);   // siempre es un alta: nunca pisa un ilícito existente
        validar(ilicito);
        return guardarYValidarTipos(ilicito);
    }

    @Override
    @Transactional
    public Ilicito modificarIlicito(Integer id, Ilicito detalles) {
        Ilicito existente = traerIlicitoPorId(id);
        validar(detalles);

        existente.setDescripcion(detalles.getDescripcion());
        existente.setAnosCondena(detalles.getAnosCondena());
        existente.setIdDelito(detalles.getIdDelito());
        existente.setIdTipoJuridico(detalles.getIdTipoJuridico());
        existente.setIdTipoAccion(detalles.getIdTipoAccion());
        existente.setIdTipoIntencion(detalles.getIdTipoIntencion());
        return guardarYValidarTipos(existente);
    }

    @Override
    @Transactional
    public void eliminarIlicito(Integer id) {
        Ilicito existente = traerIlicitoPorId(id);   // 404 si no existe

        long enUso = expedienteRepository.countByIlicitoId(id);
        if (enUso > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el ilícito: lo usan " + enUso + " expediente(s).");
        }
        ilicitoRepository.delete(existente);
    }

    // ------------------------------------------------------------------
    // Validaciones (devuelven 400 en vez de un 500 de la base)
    // ------------------------------------------------------------------
    private void validar(Ilicito i) {
        if (i.getAnosCondena() == null || i.getAnosCondena() < 0) {
            throw datoInvalido("Los años de condena son obligatorios y no pueden ser negativos.");
        }
        if (i.getIdDelito() == null) {
            throw datoInvalido("Elegí un delito del catálogo.");
        }

        Delito delito = delitoRepository.findById(i.getIdDelito())
                .orElseThrow(() -> datoInvalido("El delito seleccionado no existe."));
        // Se mantienen los campos históricos sincronizados con el catálogo.
        i.setDescripcion(delito.getDescripcion());
        i.setIdTipoJuridico(delito.getIdTipoJuridico());
        i.setIdTipoAccion(delito.getIdTipoAccion());
        i.setIdTipoIntencion(delito.getIdTipoIntencion());
    }

    // saveAndFlush fuerza el INSERT/UPDATE acá, así la base avisa ya si algún tipo no existe
    private Ilicito guardarYValidarTipos(Ilicito i) {
        try {
            return ilicitoRepository.saveAndFlush(i);
        } catch (DataIntegrityViolationException e) {
            throw datoInvalido("El delito o alguno de sus tipos no existe en el catálogo.");
        }
    }

    private ResponseStatusException datoInvalido(String mensaje) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
    }
}
