package am2.hu.ezshop.extensions

import android.content.SharedPreferences


fun SharedPreferences.putValue(key: String, value: Any) {
    with(this.edit())
    {
        when (value) {
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException("This typ can't be save in SharedPreferences")
        }.apply()
    }
}

fun <T> SharedPreferences.getValue(key: String, default: T): T {
    val result: Any = when (default) {
        is Int -> getInt(key, default)
        is Long -> getLong(key, default)
        is Float -> getFloat(key, default)
        is String -> getString(key, default)
        is Boolean -> getBoolean(key, default)
        else -> throw IllegalArgumentException("This type is not in SharedPreferences")
    }

    return result as T
}