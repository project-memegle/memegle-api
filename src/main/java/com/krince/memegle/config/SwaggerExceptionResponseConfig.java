package com.krince.memegle.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerExceptionResponseConfig {

    public Components getExceptionResponseComponents() {
        Components components = new Components();

        components.addResponses("40000", createApiResponse(
                "올바르지 않은 요청",
                "{\"success\": false, \"status\": \"Bad Request\", \"code\": 40000, \"message\": \"올바르지 않은 요청입니다.\"}"
        ));

        components.addResponses("40001", createApiResponse(
                "올바르지 않은 양식",
                "{\"success\": false, \"status\": \"Invalid Value\", \"code\": 40001, \"message\": \"요청 값이 올바르지 않습니다.\"}"
        ));

        components.addResponses("40002", createApiResponse(
                "중복된 회원",
                "{\"success\": false, \"status\": \"Duplicate User\", \"code\": 40002, \"message\": \"이미 존재하는 회원입니다.\"}"
        ));

        components.addResponses("40003", createApiResponse(
                "필수값 누락",
                "{\"success\": false, \"status\": \"Require Value\", \"code\": 40003, \"message\": \"필수 입력값이 누락되었습니다.\"}"
        ));

        components.addResponses("40004", createApiResponse(
                "중복된 리소스",
                "{\"success\": false, \"status\": \"Duplicate Resource\", \"code\": 40004, \"message\": \"이미 존재하는 리소스입니다.\"}"
        ));

        components.addResponses("40100", createApiResponse(
                "인증 정보 불일치",
                "{\"success\": false, \"status\": \"Unauthorized\", \"code\": 40100, \"message\": \"인증 정보가 일치하지 않습니다.\"}"
        ));

        components.addResponses("40101", createApiResponse(
                "유효하지 않은 토큰",
                "{\"success\": false, \"status\": \"Invalid Token\", \"code\": 40101, \"message\": \"유효하지 않은 토큰입니다.\"}"
        ));

        components.addResponses("40102", createApiResponse(
                "틀린 비밀번호",
                "{\"success\": false, \"status\": \"Invalid Password\", \"code\": 40102, \"message\": \"비밀번호를 확인해주세요.\"}"
        ));

        components.addResponses("40103", createApiResponse(
                "토큰 정보 누락",
                "{\"success\": false, \"status\": \"Empty Token\", \"code\": 40103, \"message\": \"토큰이 없습니다.\"}"
        ));

        components.addResponses("40104", createApiResponse(
                "만료된 토큰",
                "{\"success\": false, \"status\": \"Expired Token\", \"code\": 40104, \"message\": \"만료된 토큰입니다.\"}"
        ));

        components.addResponses("40105", createApiResponse(
                "이메일 인증코드 불일치",
                "{\"success\": false, \"status\": \"Invalid Authentication Code\", \"code\": 40105, \"message\": \"이메일 인증코드가 일치하지 않습니다.\"}"
        ));

        components.addResponses("40106", createApiResponse(
                "이메일 인증코드 정보 없음",
                "{\"success\": false, \"status\": \"No Such Authentication Code\", \"code\": 40106, \"message\": \"인증 이메일 정보가 없거나 만료기간이 초과되었습니다.\"}"
        ));

        components.addResponses("40300", createApiResponse(
                "접근 권한 없음",
                "{\"success\": false, \"status\": \"Forbidden\", \"code\": 40300, \"message\": \"해당 리소스에 접근 권한이 없습니다.\"}"
        ));

        components.addResponses("40401", createApiResponse(
                "존재하지 않는 리소스",
                "{\"success\": false, \"status\": \"Not Found Resource\", \"code\": 40401, \"message\": \"리소스가 존재하지 않습니다.\"}"
        ));

        components.addResponses("50000", createApiResponse(
                "알 수 없는 에러",
                "{\"success\": false, \"status\": \"Internal Server Error\", \"code\": 50000, \"message\": \"서버 에러입니다 개발자에게 문의해주세요.\"}"
        ));

        return components;
    }

    private ApiResponse createApiResponse(String description, String example) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>()).example(example)));
    }
}
