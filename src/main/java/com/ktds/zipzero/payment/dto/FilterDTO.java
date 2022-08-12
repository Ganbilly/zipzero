package com.ktds.zipzero.payment.dto;

import java.time.LocalDate;

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
public class FilterDTO {
    @Builder.Default
    private LocalDate startTime = LocalDate.parse("1000-01-01");

    private LocalDate endTime;
    private LocalDate ptime;

    @Builder.Default
    private String hq = "";
    @Builder.Default
    private String dept = "";
    @Builder.Default
    private String team = "";
    @Builder.Default
    private String mname = "";

    private long mid;
    @Builder.Default
    private int pcardtype = 2;
    @Builder.Default
    private String pname = "";
    @Builder.Default
    private String minptotalprice = "0";
    @Builder.Default
    private String maxptotalprice = "9999999";
    private String ptotalprice;

    public LocalDate getEndTime() {
        if (this.endTime == null) {
            return LocalDate.ofYearDay(2025, 1);
        }
        return this.endTime;
    }
}
