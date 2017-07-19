package com.example.demo.function;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;
import org.springframework.data.gemfire.function.annotation.RegionData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@OnRegion(region="employee")
public interface FunctionExecution {
    @FunctionId("greeting")
    public void execute(String message);

}
