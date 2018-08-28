package com.meetferrytan.kotlinmvptemplate.base.di.scopes

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by ferrytan on 7/4/17.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScope