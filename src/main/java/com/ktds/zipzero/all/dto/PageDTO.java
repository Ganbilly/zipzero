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
public class PageDTO {
    @Builder.Default
    int page = 1;

    @Builder.Default
    int size = 10;

    @Builder.Default
    int total = 0;

    // 시작 페이지 번호, 끝 페이지 번호
    int start, end;
    // 이전 페이지 존재 여부, 다음 페이지 존재 여부
    boolean prev, next;

    public void setPaging() {
        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
        this.start = this.end - 9;

        int last = (int)(Math.ceil((total / (double)size)));
        this.end = end > last ? last : end;
        
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }

    public int getSkip() {
        return (page - 1) * size;
    }
}
