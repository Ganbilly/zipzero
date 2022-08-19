package com.ktds.zipzero.member.dto;

public enum MemberRole {
    USER(1), TEAM(2), DEPARTMENT(3), HEADQUARTER(4), ADMIN(5);
    private int no;
    private MemberRole(int num) {
        this.no = num;
    }
    public int getEnum() {
        return no;
    }
}
