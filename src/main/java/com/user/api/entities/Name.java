package com.user.api.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Name {
    @NotBlank
    private String f_name;
    @Nullable
    private String m_name;
    @NotEmpty
    private String l_name;
}
