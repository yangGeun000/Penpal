package com.penpal.project.answer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
    // by 조성빈, 메시지 변경
    @NotEmpty(message = "Enter your Content")
    @Size(max=150)
    private String content;
}
