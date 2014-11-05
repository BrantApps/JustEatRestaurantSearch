package com.brantapps.justeatsearch.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Application context qualifier.
 *
 * Created by david.branton on 27/05/2014.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {}