package com.example.demo;

import com.example.demo.function.FunctionExecution;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.gemstone.gemfire.cache.DataPolicy;
import com.gemstone.gemfire.cache.GemFireCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableGemfireRepositories(basePackages = "com.example.demo.repository")
@EnableGemfireFunctions
@EnableGemfireFunctionExecutions(basePackages = "com.example.demo.function")
public class GemfireConfiguration {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    FunctionExecution functionExecution;


    @Bean
    Properties gemfireProperties() {
        Properties gemfireProperties = new Properties();
        gemfireProperties.setProperty("name", "SpringDataGemFireApplication");
        gemfireProperties.setProperty("mcast-port", "0");
        gemfireProperties.setProperty("log-level", "config");
        return gemfireProperties;
    }

    @Bean
    @Autowired
    CacheFactoryBean gemfireCache() {
        CacheFactoryBean gemfireCache = new CacheFactoryBean();
        gemfireCache.setClose(true);
        gemfireCache.setProperties(gemfireProperties());
        return gemfireCache;
    }


    @Bean(name="employee")
    @Autowired
    LocalRegionFactoryBean<String, Employee> getEmployee(final GemFireCache cache) {
        LocalRegionFactoryBean<String, Employee> employeeRegion = new LocalRegionFactoryBean<String, Employee>();
        employeeRegion.setCache(cache);
        employeeRegion.setClose(false);
        employeeRegion.setName("employee");
        employeeRegion.setPersistent(false);
        employeeRegion.setDataPolicy(DataPolicy.PRELOADED);
        return employeeRegion;
    }


}

