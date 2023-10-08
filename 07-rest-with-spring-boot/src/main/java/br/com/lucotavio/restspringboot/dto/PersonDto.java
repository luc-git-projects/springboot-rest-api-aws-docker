package br.com.lucotavio.restspringboot.dto;

import java.io.Serializable;

public record PersonDto(Long id, String firstName, String lastName, String address, String gender) implements Serializable {
}