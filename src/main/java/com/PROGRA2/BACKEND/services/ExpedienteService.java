package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Expediente;
import com.PROGRA2.BACKEND.models.Ilicito;
import com.PROGRA2.BACKEND.repository.ExpedienteRepository;
import com.PROGRA2.BACKEND.repository.IlicitoRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExpedienteService implements IExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final IlicitoRepository ilicitoRepository;
    private final IIlicitoService ilicitoService;

    public ExpedienteService(ExpedienteRepository expedienteRepository,
                             IlicitoRepository ilicitoRepository,
                             IIlicitoService ilicitoService) {
        this.expedienteRepository = expedienteRepository;
        this.ilicitoRepository = ilicitoRepository;
        this.ilicitoService = ilicitoService;
    }

    @Override
    public List<Expediente> traerTodosLosExpedientes() {
        return expedienteRepository.findAll();
    }

    @Override
    public Expediente traerExpedientePorId(Integer nroExpediente) {
        return expedienteRepository.findById(nroExpediente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un expediente con el número " + nroExpediente));
    }

    @Override
    @Transactional
    public Expediente guardarExpediente(Expediente expediente) {
        expediente.setNroExpediente(null);   // siempre es un alta: nunca pisa un expediente existente
        prepararParaGuardar(expediente);
        return expedienteRepository.save(expediente);
    }

    @Override
    @Transactional
    public Expediente modificarExpediente(Integer nroExpediente, Expediente detalles) {
        Expediente existente = traerExpedientePorId(nroExpediente);
        prepararParaGuardar(detalles);

        existente.setFecha(detalles.getFecha());
        existente.setCodigoSucursal(detalles.getCodigoSucursal());
        existente.setClaveJuez(detalles.getClaveJuez());
        existente.getClavesDelincuentes().clear();                      // se reemplaza la lista completa
        existente.getClavesDelincuentes().addAll(detalles.getClavesDelincuentes());
        existente.setIlicito(detalles.getIlicito());
        existente.setDescripcion(detalles.getDescripcion());
        existente.setEsCulpable(detalles.getEsCulpable());
        existente.setTieneCondena(detalles.getTieneCondena());
        existente.setAnosCondena(detalles.getAnosCondena());
        existente.setAnosVigenciaCondena(detalles.getAnosVigenciaCondena());
        existente.setCondenaVigente(detalles.getCondenaVigente());
        return expedienteRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminarExpediente(Integer nroExpediente) {
        Expediente existente = traerExpedientePorId(nroExpediente);   // 404 si no existe
        expedienteRepository.delete(existente);
    }

    // ------------------------------------------------------------------
    // Validaciones y valores por defecto (devuelven 400 en vez de un 500 de la base)
    // ------------------------------------------------------------------
    private void prepararParaGuardar(Expediente e) {
        if (e.getFecha() == null) {
            throw datoInvalido("La fecha es obligatoria.");
        }
        if (e.getCodigoSucursal() == null) {
            throw datoInvalido("El código de sucursal es obligatorio.");
        }
        if (estaVacio(e.getClaveJuez())) {
            throw datoInvalido("La clave del juez es obligatoria.");
        }
        e.setClavesDelincuentes(normalizarDelincuentes(e.getClavesDelincuentes()));
        if (e.getIlicito() == null) {
            throw datoInvalido("El expediente debe tener un ilícito.");
        }

        e.setClaveJuez(e.getClaveJuez().trim());

        if (e.getEsCulpable() == null) e.setEsCulpable(false);
        if (e.getTieneCondena() == null) e.setTieneCondena(false);
        if (e.getCondenaVigente() == null) e.setCondenaVigente(false);
        if (e.getAnosCondena() == null) e.setAnosCondena(0);
        if (e.getAnosVigenciaCondena() == null) e.setAnosVigenciaCondena(0);

        if (e.getAnosCondena() < 0 || e.getAnosVigenciaCondena() < 0) {
            throw datoInvalido("Los años de condena y de vigencia no pueden ser negativos.");
        }

        // Último paso (así, si algo de arriba falla, no se llega a tocar ningún ilícito)
        e.setIlicito(resolverIlicito(e.getIlicito()));
    }

    /**
     * El expediente trae su ilícito de tres formas posibles:
     *  - sin id                      -> se CREA un ilícito nuevo (alta simultánea con el expediente)
     *  - con id y con sus datos      -> se ACTUALIZA ese ilícito con los datos recibidos
     *  - con id solamente            -> se USA el ilícito existente
     * Todo corre dentro de la misma transacción: si el expediente falla, el ilícito no queda guardado.
     */
    private Ilicito resolverIlicito(Ilicito recibido) {
        Integer id = recibido.getId();
        if (id == null) {
            return ilicitoService.guardarIlicito(recibido);
        }
        Ilicito existente = ilicitoRepository.findById(id)
                .orElseThrow(() -> datoInvalido("No existe un ilícito con id " + id + "."));
        if (recibido.getDescripcion() != null) {
            return ilicitoService.modificarIlicito(id, recibido);
        }
        return existente;
    }

    // Recorta, descarta vacíos y repetidos (respetando el orden). Exige al menos un delincuente.
    private List<String> normalizarDelincuentes(List<String> recibidas) {
        Set<String> claves = new LinkedHashSet<>();
        if (recibidas != null) {
            for (String c : recibidas) {
                if (c != null && !c.trim().isEmpty()) {
                    String limpia = c.trim();
                    if (limpia.length() > 20) {
                        throw datoInvalido("La clave de delincuente '" + limpia + "' supera los 20 caracteres.");
                    }
                    claves.add(limpia);
                }
            }
        }
        if (claves.isEmpty()) {
            throw datoInvalido("Elegí al menos un delincuente.");
        }
        return new ArrayList<>(claves);
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private ResponseStatusException datoInvalido(String mensaje) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
    }
}
