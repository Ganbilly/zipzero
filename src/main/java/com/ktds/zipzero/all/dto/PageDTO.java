package com.ktds.zipzero.all.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDTO {
    @Builder.Default
    int page = 1;
    int size = 10;

    int getSkip() {
        return (page - 1) * size;
    }
}
