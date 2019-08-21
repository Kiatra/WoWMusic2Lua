package com.github.zanony.WoWMusic2Lua;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

public class FilePathValidator implements IParameterValidator {

        public void validate(String name, String value) throws ParameterException {
            if (!new File(value).exists()) {
                throw new ParameterException("Parameter " + name + " should be an existing file (File " + value +") not found");
            }
        }

}
