
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Sucursal;
import com.PROGRA2.BACKEND.services.ISucursalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // POST http://localhost:8080/api/sucursales
    @PostMapping
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.guardarSucursal(sucursal);
    }
    
    // DELETE http://localhost:8080/api/sucursales/1   (204 si se eliminó, 404 si no existe)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable("id") Integer nroSucursal) {
        sucursalService.eliminarSucursal(nroSucursal);
        return ResponseEntity.noContent().build();
    }
    
    // PUT http://localhost:8080/api/sucursales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> modificarSucursal(@PathVariable Integer id, @RequestBody Sucursal sucursalDetalles) {
        Sucursal delincuenteActualizado = sucursalService.modificarSucursal(id, sucursalDetalles);
        return ResponseEntity.ok(delincuenteActualizado);
    }
}