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
    private LocalDate startTime = LocalDate.parse("0001-01-01");
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

    @Builder.Default
    private String mid = "";
    private String empno = "";
    @Builder.Default
    private int pcardtype = 2;
    @Builder.Default
    private String pname = "";
    @Builder.Default
    private String sname = "";
    @Builder.Default
    private String minptotalprice = "0";
    @Builder.Default
    private String maxptotalprice = "9999999";
    private String ptotalprice;
    @Builder.Default
    private String sid = "";

    public LocalDate getEndTime() {
        if (this.endTime == null) {
            return LocalDate.now();
        }
        return this.endTime;
    }

    public String getCardType() {
        switch(pcardtype) {
            case 0:
                return "법인";
            case 1:
                return "개인";
            default:
                return "";
        }
    }

    public String getSnameBySid() {
        switch(sid) {
            case "1":
                return "승인";
            case "2":
                return "반려";
            case "3":
                return "대기";
            case "4":
                return "취소";
            default:
                return "";
        }
    }
}
