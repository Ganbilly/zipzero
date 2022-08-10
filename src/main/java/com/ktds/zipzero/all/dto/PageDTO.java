package com.ktds.zipzero.all.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    @Builder.Default
    int page = 1;
    int size = 10;

    int getSkip() {
        return (page - 1) * size;
    }
}
