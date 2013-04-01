package com.harmony.validation;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 29/03/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InputValidator<T> {
    boolean isValid(T data, List<String> errors);
}
