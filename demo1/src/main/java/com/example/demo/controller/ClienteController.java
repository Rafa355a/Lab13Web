package com.example.demo.controller;


import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")

public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("clientes")
    public List<Cliente> ListarClientes(){
        return clienteRepository.findAll();
    }

    @PostMapping("/clientes")
    public Cliente guardarCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> ListarClientePorId(@PathVariable long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("El cliente no existe"+ id));
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> ActualizarCliente(@PathVariable long id, @RequestBody Cliente clienteRequest){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("El cliente no existe"+ id));

        cliente.setNombre(clienteRequest.getNombre());
        cliente.setApellidos(clienteRequest.getApellidos());
        cliente.setEmail(clienteRequest.getEmail());

        Cliente clienteActualizado= clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Boolean>> EliminarCliente(@PathVariable long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("El cliente no existe"+ id));

        clienteRepository.delete(cliente);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
