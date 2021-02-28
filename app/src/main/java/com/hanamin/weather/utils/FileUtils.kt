package com.hanamin.weather.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

class FileUtils constructor(private val context: Context) {

    fun writeToFile(fileName: String?, value: String): Boolean {
        val fos: FileOutputStream
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(value.toByteArray())
            fos.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    fun ReadFile(fileName: String?): String? {
        val fis: FileInputStream
        val storedString = StringBuffer()
        try {
            fis = context.openFileInput(fileName)
            val dataIO = DataInputStream(fis)
            val reader = BufferedReader(InputStreamReader(fis))
            var strLine: String?
            while (reader.readLine().also { strLine = it } != null) {
                storedString.append(strLine)
            }
            reader.close()
            dataIO.close()
            fis.close()
        } catch (e: Exception) {
        }
        return storedString.toString()
    }


    inline fun <reified T : Any> jsonToArrayObj(json: String): T {
        val itemType = object : TypeToken<T>() {}.type
        return Gson().fromJson(ReadFile(json), itemType)
    }

    inline fun <reified T : Any> jsonToObj(json: String): T =
        Gson().fromJson(ReadFile(json), T::class.java)

    inline fun <reified T : Any> objToJson(obj: T): String =
        Gson().toJson(obj)


}