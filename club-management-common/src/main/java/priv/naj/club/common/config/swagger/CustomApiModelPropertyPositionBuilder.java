package priv.naj.club.common.config.swagger;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;

import static springfox.documentation.swagger.schema.ApiModelProperties.findApiModePropertyAnnotation;

import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.google.common.base.Optional;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

/**
 * 添加ApiModel的position
 */
//@Component
//public class CustomApiModelPropertyPositionBuilder implements ModelPropertyBuilderPlugin {
//    private Log log = LogFactory.getLog(getClass());
//
//    @Override
//    public boolean supports(DocumentationType delimiter) {
//        return SwaggerPluginSupport.pluginDoesApply(delimiter);
//    }
//
//    @Override
//    public void apply(ModelPropertyContext context) {
//        Optional<BeanPropertyDefinition> beanPropertyDefinitionOpt = context.getBeanPropertyDefinition();
//        Optional<ApiModelProperty> annotation = Optional.absent();
//        if (context.getAnnotatedElement().isPresent()) {
//            annotation = annotation.or(findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
//        }
//        if (context.getBeanPropertyDefinition().isPresent()) {
//            annotation = annotation.or(
//                    findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class));
//        }
//        if (beanPropertyDefinitionOpt.isPresent()) {
//            BeanPropertyDefinition beanPropertyDefinition = beanPropertyDefinitionOpt.get();
//            if (annotation.isPresent() && annotation.get().position() != 0) {
//                return;
//            }
//            AnnotatedField field = beanPropertyDefinition.getField();
//            Class<?> clazz = field.getDeclaringClass();
//            Field[] declaredFields = clazz.getDeclaredFields();
//            Field declaredField;
//            try {
//                declaredField = clazz.getDeclaredField(field.getName());
//            } catch (NoSuchFieldException | SecurityException e) {
//                log.error("", e);
//                return;
//            }
//            int indexOf = ArrayUtils.indexOf(declaredFields, declaredField);
//            if (indexOf != -1) {
//                context.getBuilder().position(indexOf);
//            }
//        }
//    }
//}