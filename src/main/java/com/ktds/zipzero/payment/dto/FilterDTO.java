package com.ktds.zipzero.payment.dto;

import java.util.Date;

import lombok.AccessLevel;
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
    private Date startTime = null;
    @Builder.Default
    private Date endTime = null;

    private String hq;
    private String dept;
    private String team;
    private String mid;
    private String mname;

    private int pcardtype;
    private String pname;
    private String ptotalprice;
}
