package com.meetferrytan.kotlinmvptemplate.base.di.scopes

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by ferrytan on 29/7/17.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ServiceScope