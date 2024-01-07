package com.heyqing.disk.web.validator;

import com.heyqing.disk.core.constants.HeyDiskConstants;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * ClassName:WebValidatorConfig
 * Package:com.heyqing.disk.web.validator
 * Description:
 *          统一的参数校验器
 * @Date:2024/1/7
 * @Author:Heyqing
 */
@SpringBootConfiguration
@Log4j2
public class WebValidatorConfig {

    private static final String FAIL_FAST_KEY = "hibernate.validator.fail_fast";

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(HeyDiskValidator());
        log.info("The hibernate validator is loaded successfully!");
        return postProcessor;
    }

    /**
     * 构造项目的方法参数校验器
     * @return
     */
    private Validator HeyDiskValidator() {

        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty(FAIL_FAST_KEY, HeyDiskConstants.TRUE_STR)
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
}
