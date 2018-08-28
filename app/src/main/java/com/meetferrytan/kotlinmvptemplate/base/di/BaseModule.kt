package com.meetferrytan.kotlinmvptemplate.base.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.google.gson.*
import com.meetferrytan.kotlinmvptemplate.MyApplication
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by ferrytan on 10/18/17.
 */

@Module
abstract class BaseModule {
    @Binds
    abstract fun application(app: MyApplication): Application

    class CollectionAdapter : JsonSerializer<Collection<*>> {
        override fun serialize(src: Collection<*>?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
            if (src == null || src.isEmpty())
            // exclusion is made here
                return null

            val array = JsonArray()

            for (child in src) {
                val element = context.serialize(child)
                array.add(element)
            }

            return array
        }
    }

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        @Named(Constants.DATABASE_NAME)
        fun provideDatabaseName(): String = "database.db"

        @JvmStatic
        @Provides
        @Singleton
        @Named(Constants.PREF_DEFAULT)
        fun provideDefaultSharedPreferences(application: Application): SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(application)

        @JvmStatic
        @Provides
        @Singleton
        fun provideGson(collectionAdapter: CollectionAdapter): Gson {
            val gsonBuilder = GsonBuilder()
                    .registerTypeHierarchyAdapter(Collection::class.java, collectionAdapter)
                    .serializeNulls()
                    .disableHtmlEscaping()
            return gsonBuilder.create()
        }

        @JvmStatic
        @Provides
        @Singleton
        fun provideCollectionAdapter(): CollectionAdapter = CollectionAdapter()

        @JvmStatic
        @Provides
        @Singleton
        fun provideConnectivityManager(application: Application): ConnectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        @JvmStatic
        @Provides
        @Singleton
        fun provideNotificationManager(application: Application): NotificationManager =
                application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }
}