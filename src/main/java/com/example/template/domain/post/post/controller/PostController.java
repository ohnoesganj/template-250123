package com.example.template.domain.post.post.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("");
    }

    @AllArgsConstructor
    @Getter
    public static class WriteForm {
        @NotBlank(message = "제목을 입력해주세요.")
        @Length(min = 5, message = "제목은 5글자 이상 작성해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        @Length(min = 10, message = "내용은 10글자 이상 작성해주세요.")
        private String content;
    }

    @PostMapping("/write")
    @ResponseBody
    public String doWrite(@Valid WriteForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));

            return getFormHtml(errorMessage);
        }

        return """
                <h1>게시물 조회</h1>
                <div>%s</div>
                <div>%s</div>
                """.formatted(form.title, form.content);
    }

    private String getFormHtml(String errorMsg) {
        return """
                <div>%s</div>
                <form method="post">
                    <input type="text" name="title" placeholder="제목" /> <br>
                    <textarea name="content"></textarea> <br>
                    <input type="submit" value="등록" /> <br>
                </form>
                """.formatted(errorMsg);
    }
}
