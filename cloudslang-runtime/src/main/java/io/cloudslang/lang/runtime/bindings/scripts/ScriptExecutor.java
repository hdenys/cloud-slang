/**
 * ****************************************************************************
 * (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 * <p/>
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * *****************************************************************************
 */
package io.cloudslang.lang.runtime.bindings.scripts;

import io.cloudslang.lang.entities.bindings.values.Value;
import io.cloudslang.lang.entities.bindings.values.ValueFactory;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Bonczidai Levente
 * @since 1/19/2016
 */
@Component
public class ScriptExecutor extends AbstractScriptInterpreter {

    @Autowired
    @Qualifier("execInterpreter")
    private PythonInterpreter interpreter;

    //we need this method to be synchronized so we will not have multiple scripts run in parallel on the same context
    public synchronized Map<String, Value> executeScript(String script, Map<String, Value> callArguments) {
        try {
            cleanInterpreter(interpreter);
            prepareInterpreterContext(callArguments);
            return exec(interpreter, script);
        } catch (Exception e) {
            throw new RuntimeException("Error executing python script: " + e, e);
        }
    }

    private void prepareInterpreterContext(Map<String, Value> context) {
        for (Map.Entry<String, Value> entry : context.entrySet()) {
            interpreter.set(entry.getKey(), ValueFactory.createPyObjectValue(entry.getValue()));
        }
    }

}
