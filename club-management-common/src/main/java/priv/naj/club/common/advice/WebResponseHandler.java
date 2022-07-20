package priv.naj.club.common.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import priv.naj.club.common.response.CommonResult;
import priv.naj.club.common.response.ErrorResult;
import priv.naj.club.common.response.NoCommonResponse;
import springfox.documentation.swagger2.web.Swagger2Controller;

/**
 * 实现ResponseBodyAdvice接口
 **/
@ControllerAdvice(basePackages = "priv.naj.club")
public class WebResponseHandler implements ResponseBodyAdvice<Object> {
    /**
     * 是否支持advice功能
     * true=支持，false=不支持
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getContainingClass().equals(Swagger2Controller.class)) {
            return false;
        } else {
            NoCommonResponse noCommonResponse = methodParameter.getMethod().getAnnotation(
                    NoCommonResponse.class);
            if (noCommonResponse != null) {
                return false;
            } else {
                noCommonResponse = methodParameter.getContainingClass().getAnnotation(NoCommonResponse.class);
                return noCommonResponse == null;
            }
        }
    }

    /**
     * 处理response的具体业务方法
     * @param body:方法的返回值
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof ErrorResult) {
            ErrorResult errorResult = (ErrorResult) body;
            return new CommonResult<>(errorResult.getCode(), errorResult.getMessage());
        } else if (body instanceof CommonResult) {
            return body;
        } else {
            CommonResult result = new CommonResult();
            result.setCode("0");
            result.setData(body);
            result.setMsg("成功");
            return result;
        }
    }
}
