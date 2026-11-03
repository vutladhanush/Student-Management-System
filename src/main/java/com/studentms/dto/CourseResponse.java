package com.studentms.dto;

public class CourseResponse {

    private Long id;
    private String code;
    private String title;
    private String description;
    private Integer credits;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String code, String title, String description, Integer credits) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCredits() {
        return credits;
    }
}
