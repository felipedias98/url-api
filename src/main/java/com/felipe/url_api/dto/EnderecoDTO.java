package com.felipe.url_api.dto;

import java.time.LocalDateTime;

public record EnderecoDTO(
    Long id,
    String original_url,
    String short_code,
    Long acess_count,
    LocalDateTime created_at,
    LocalDateTime updated_at
) {

}
