package com.user.api.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Name {
    private String f_name;
    @Nullable
    private String m_name;
    private String l_name;
}
