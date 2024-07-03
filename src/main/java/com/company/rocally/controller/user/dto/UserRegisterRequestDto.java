package com.company.rocally.controller.user.dto;

import com.company.rocally.common.Period;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterRequestDto {

    @NotBlank(message = "이름을 입력하세요.")
    private String username;

    private String yyyy;
    private String mm;
    private String dd;
    @NotNull(message = "생년월일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber;

    @NotBlank(message = "필수항목 입니다.")
    private String discoveryRoute;

    public String getBirthDateString() {
        if (birthDate != null) {
            return birthDate.format(Period.yyyyMMdd);
        } else {
            return "";
        }
    }

    @ModelAttribute
    public void setBirthDate() {
        if (yyyy != null && mm != null && dd != null) {
            this.birthDate = LocalDate.of(
                    Integer.parseInt(yyyy),
                    Integer.parseInt(mm),
                    Integer.parseInt(dd)
            );
        }
    }
}
