package com.jdc;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jdc.domain.BaseRepositoryImpl;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = "com.jdc.domain.repo")
public class CoreDomainConfiguration {

}
