package com.felipe.url_api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.url_api.dto.EnderecoDTO;
import com.felipe.url_api.model.Endereco;
import com.felipe.url_api.service.EnderecoService;

@RestController 
public class EnderecoController {
    private EnderecoService enderecoService;
    
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }
    
    @GetMapping ("/shorten/")
    public ResponseEntity<List<Endereco>> listarEnderecos(){
        return ResponseEntity.ok(enderecoService.listar());
    }
    @GetMapping ("/shorten/{short_code}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable String short_code){
        EnderecoDTO dto = enderecoService.buscar(short_code);
        if(dto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping ("/shorten/")
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco){
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.cadastrar(endereco));
    }

    @DeleteMapping ("/shorten/{short_code}")
    public ResponseEntity<Void> excluirEndereco(@PathVariable String short_code){
        EnderecoDTO dto = enderecoService.buscar(short_code);

        if(dto==null){
            return ResponseEntity.notFound().build();
        }

        enderecoService.excluir(short_code);

        return ResponseEntity.noContent().build();
    }
    @PutMapping ("/shorten/{short_code}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable String short_code,@RequestBody Endereco novoEndereco)
    {   
        Endereco enderecoAtualizado = enderecoService.atualizar(short_code, novoEndereco.getOriginal_url());
       
        if(enderecoAtualizado==null){
            return ResponseEntity.notFound().build();
        }
        EnderecoDTO dtoAtualizado = new EnderecoDTO(
            enderecoAtualizado.getId(),
            enderecoAtualizado.getOriginal_url(),
            enderecoAtualizado.getShort_code(),
            enderecoAtualizado.getAcess_count(),
            enderecoAtualizado.getCreated_at(),
            enderecoAtualizado.getUpdated_at()
        );
        
        

        return ResponseEntity.ok(dtoAtualizado);
    }
}
