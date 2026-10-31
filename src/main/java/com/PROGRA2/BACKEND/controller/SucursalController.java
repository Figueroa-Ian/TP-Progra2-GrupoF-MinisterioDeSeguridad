
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Sucursal;
import com.PROGRA2.BACKEND.services.ISucursalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/sucursales")
public class SucursalController {
    
    @Autowired
    private ISucursalService sucursalService;

    //GET http://localhost:8080/api/sucursales
    @GetMapping
    public List<Sucursal> verSucursales() {
        return sucursalService.traerTodasLasSucursales();
    }

    // GET http://localhost:8080/api/sucursales/1
    @GetMapping("/{id}")
    public Sucursal verSucursalPorId(@PathVariable Integer id) {
        return sucursalService.traerSucursalPorId(id);
    }

    @PostMapping("/crear/sucursal")
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.guardarSucursal(sucursal);
    }

    // PUT http://localhost:8080/api/sucursales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> modificarSucursal(@PathVariable Integer id, @RequestBody Sucursal delincuenteDetalles) {
        Sucursal delincuenteActualizado = sucursalService.modificarSucursal(id, delincuenteDetalles);
        return ResponseEntity.ok(delincuenteActualizado);
    }
}