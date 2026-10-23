package com.felipe.url_api.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.felipe.url_api.dto.EnderecoDTO;
import com.felipe.url_api.model.Endereco;
import com.felipe.url_api.repository.EnderecoRepository;

import tools.jackson.databind.node.StringNode;

@Service
public class EnderecoService {
    private EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public static String gerarCodigoAleatorio() {
        Random random = new Random();
        List<Character> caracteres = new ArrayList<>();

        // Adiciona 3 letras maiúsculas aleatórias (A-Z)
        for (int i = 0; i < 3; i++) {
            char letra = (char) (random.nextInt(26) + 'A');
            caracteres.add(letra);
        }

        // Adiciona 3 números aleatórios (0-9)
        for (int i = 0; i < 3; i++) {
            char numero = (char) (random.nextInt(10) + '0');
            caracteres.add(numero);
        }

        // Embaralha os caracteres para criar uma ordem aleatória
        Collections.shuffle(caracteres);

        // Converte a lista de caracteres em uma String
        StringBuilder sb = new StringBuilder();
        for (char c : caracteres) {
            sb.append(c);
        }

        return sb.toString();
    }
    
    public Endereco cadastrar(Endereco endereco){
       // endereco.setCreated_at(ldt);
       // endereco.setUpdated_at(ldt);
        endereco.setShort_code(gerarCodigoAleatorio());
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listar(){
        return enderecoRepository.findAll();
    }

    public EnderecoDTO buscar(String short_code){
        Endereco e = buscarObjetoEndereco(short_code);
        if(e==null){
            return null;
        }
        else{
            return new EnderecoDTO(
            e.getId(),
            e.getOriginal_url(),
            e.getShort_code(),
            e.getAcess_count(),
            e.getCreated_at(),
            e.getUpdated_at()
        );
        }
    }
    public void excluir(String short_code){
        enderecoRepository.deleteById(buscarObjetoEndereco(short_code).getId());
    }

    private Endereco buscarObjetoEndereco(String short_code){
        List<Endereco> lista = enderecoRepository.findAll();
        Endereco e = null;

        for (Endereco obj : lista) {
            if(obj.getShort_code().equals(short_code)){
                e = obj;
                break;
            }
        }
        return e;
    }

    public Endereco atualizar(String short_code, String novoEnderecoUrl){
        Endereco e = buscarObjetoEndereco(short_code);
        if (e == null) {
            return null;
        }
        e.setUpdated_at(LocalDateTime.now());
        e.setOriginal_url(novoEnderecoUrl);
        return enderecoRepository.save(e); 
    }
}
