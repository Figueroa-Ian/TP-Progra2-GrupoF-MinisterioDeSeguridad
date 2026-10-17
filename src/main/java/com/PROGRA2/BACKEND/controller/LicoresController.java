package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Licores;
import com.PROGRA2.BACKEND.repository.LicoresRepository;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class LicoresController {

    private final LicoresRepository licorRepository;

    public LicoresController(LicoresRepository licorRepository) {
        this.licorRepository = licorRepository;
    }

    //toma la ruta que le asignemos
    @GetMapping("/buscarlicores")
    // con RequestParam podemos asignar valores a la URL para poder consultar por un campo 
    // en especifico (ID) guardando el valor retornado en un string "tipo"
    // ej: //localhost:8080/buscarlicores?tipo=cerveza
    public List<Licores> buscarLicores(@RequestParam(name = "tipo", required = false) String tipo) {

        // si NO es NULL Y NO esta VACIO
        // trim .. junta todo eliminando los espacios
        // isEmpity te dice si esta o no vacío
        if (tipo != null && !tipo.trim().isEmpty()) {
            return licorRepository.findByTipo(tipo.trim());
        }
        
        //devuelve todo o devuelve nada
        return licorRepository.findAll();
    }

    @PostMapping("/licores")
    public Licores crearLicores(@RequestBody Licores nuevoLicores) {
        // Este metodo solo sirve con POSTMAN
        // El método save() ejecuta el INSERT en la base de datos
        return licorRepository.save(nuevoLicores);
    }
    
    //Crear a traves de un formulario HTML..
    //Hace un INSERT a la tabla y crea el objeto en la tabla
    @PostMapping("/licores/crear")
    public Licores crearLicoresConParams(@RequestParam String tipo, @RequestParam String marca, @RequestParam String foto) {
        Licores licor = new Licores();
        licor.setMarca(marca);
        licor.setTipo(tipo);
        licor.setFoto(foto);
        // Aquí es donde realmente ocurre el INSERT
        return licorRepository.save(licor);
    }

    /*
    @GetMapping("/buscarlicor/{id}")
    public ResponseEntity<Licores> buscarLicoresPorId(@PathVariable Long id) {
        return licorRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.<Licores>notFound().build());
    }
     */
}