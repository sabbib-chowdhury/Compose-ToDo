package com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.model.UserWidgetSettingsPreference
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object WidgetSettingsPreferenceSerializer : Serializer<UserWidgetSettingsPreference> {
    override val defaultValue: UserWidgetSettingsPreference
        get() = UserWidgetSettingsPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserWidgetSettingsPreference {
        try {
            return UserWidgetSettingsPreference.parseFrom(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: UserWidgetSettingsPreference, output: OutputStream) {
        t.writeTo(output)
    }
}
