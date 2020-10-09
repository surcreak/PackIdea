package com.surcreak.packidea.base.utils

import android.os.Build
import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Logger is a wrapper of [Log]
 * But more pretty, simple and powerful
 */
object Logger {
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private const val CHUNK_SIZE = 4000

    /**
     * It is used for json pretty print
     */
    private const val JSON_INDENT = 4

    /**
     * In order to prevent readability, max method count is restricted with 5
     */
    private const val MAX_METHOD_COUNT = 5

    /**
     * It is used to determine log settings such as method count, thread info visibility
     */
    private val settings = Settings()

    /**
     * Drawing toolbox
     */
    private const val TOP_LEFT_CORNER = '╔'
    private const val BOTTOM_LEFT_CORNER = '╚'
    private const val MIDDLE_CORNER = '╟'
    private const val HORIZONTAL_DOUBLE_LINE = '║'
    private const val DOUBLE_DIVIDER = "════════════════════════════════════════════"
    private const val SINGLE_DIVIDER = "────────────────────────────────────────────"
    private const val TOP_BORDER = TOP_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private const val BOTTOM_BORDER =
        BOTTOM_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private const val MIDDLE_BORDER = MIDDLE_CORNER.toString() + SINGLE_DIVIDER + SINGLE_DIVIDER

    /**
     * TAG is used for the Dog, the name is a little different
     * in order to differentiate the logs easily with the filter
     */
    private var TAG = "PackIdea"

    /**
     * logFileName is used for the local save log file name.
     */
    var logFileName: String? = null

    /**
     * logWriter is used for the local save log file.
     */
    private var logWriter: FileWriter? = null

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    fun init(): Settings {
        return settings
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    fun init(tag: String?, sdCardPath: String): Settings {
        if (tag == null) {
            throw NullPointerException("tag may not be null")
        }
        check(tag.trim { it <= ' ' }.isNotEmpty()) { "tag may not be empty" }
        logFileName = sdCardPath + File.separator + "log.txt"
        openLogFile()
        TAG = tag
        return settings
    }

    fun ii(msgFormat: String?) {
        val ste = Throwable().stackTrace[1]
        var traceInfo = "(" + ste.fileName + ":"
        traceInfo += "" + ste.lineNumber + "):"
        traceInfo += ste.methodName + " "
        //        Log.i("temp", traceInfo + msgFormat);
    }

    /**
     * 请求接口log专用
     *
     * @param message
     */
    fun httpLog(message: String) {
        d("HttpLog", message)
    }

    fun d(message: String) {
        d(TAG, message)
    }

    fun dSave(tag: String, message: String) {
        log(Log.DEBUG, tag, message, settings.methodCount, settings.saveLog)
    }

    fun d(message: String, methodCount: Int) {
        d(TAG, message, methodCount)
    }

    @JvmOverloads
    fun d(tag: String, message: String, methodCount: Int = settings.methodCount) {
        validateMethodCount(methodCount)
        log(Log.DEBUG, tag, message, methodCount, false)
    }

    fun e(message: String?) {
        e(TAG, message)
    }

    fun eSave(tag: String, message: String) {
        log(Log.ERROR, tag, message, settings.methodCount, settings.saveLog)
    }

    fun e(e: Exception?) {
        e(TAG, null, e, settings.methodCount)
    }

    fun e(tag: String, e: Exception?) {
        e(tag, null, e, settings.methodCount)
    }

    fun e(message: String, methodCount: Int) {
        validateMethodCount(methodCount)
        e(message, null, methodCount)
    }

    fun e(tag: String, message: String?, methodCount: Int) {
        validateMethodCount(methodCount)
        e(tag, message, null, methodCount)
    }

    @JvmOverloads
    fun e(
        tag: String,
        message: String?,
        e: Exception? = null,
        methodCount: Int = settings.methodCount
    ) {
        var message = message
        validateMethodCount(methodCount)
        if (e != null && message != null) {
            message += " : $e"
        }
        if (e != null && message == null) {
            message = e.toString()
        }
        if (message == null) {
            message = "No message/exception is set"
        }
        log(Log.ERROR, tag, message, methodCount, false)
    }

    fun w(message: String) {
        w(TAG, message)
    }

    fun wSave(tag: String, message: String) {
        log(Log.WARN, tag, message, settings.methodCount, settings.saveLog)
    }

    fun w(message: String, methodCount: Int) {
        w(TAG, message, methodCount)
    }

    @JvmOverloads
    fun w(tag: String, message: String, methodCount: Int = settings.methodCount) {
        validateMethodCount(methodCount)
        log(Log.WARN, tag, message, methodCount, false)
    }

    fun i(message: String) {
        i(TAG, message)
    }

    fun iSave(tag: String, message: String) {
        log(Log.INFO, tag, message, settings.methodCount, settings.saveLog)
    }

    fun i(message: String, methodCount: Int) {
        i(TAG, message, methodCount)
    }

    @JvmOverloads
    fun i(tag: String, message: String, methodCount: Int = settings.methodCount) {
        validateMethodCount(methodCount)
        log(Log.INFO, tag, message, methodCount, false)
    }

    fun v(message: String) {
        v(TAG, message)
    }

    fun vSave(tag: String, message: String) {
        log(Log.VERBOSE, tag, message, settings.methodCount, settings.saveLog)
    }

    fun v(message: String, methodCount: Int) {
        v(TAG, message, methodCount)
    }

    @JvmOverloads
    fun v(tag: String, message: String, methodCount: Int = settings.methodCount) {
        validateMethodCount(methodCount)
        log(Log.VERBOSE, tag, message, methodCount, false)
    }

    fun wtf(message: String) {
        wtf(TAG, message)
    }

    fun wtf(message: String, methodCount: Int) {
        wtf(TAG, message, methodCount)
    }

    @JvmOverloads
    fun wtf(tag: String, message: String, methodCount: Int = settings.methodCount) {
        validateMethodCount(methodCount)
        log(Log.ASSERT, tag, message, methodCount, false)
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    fun json(json: String) {
        json(TAG, json)
    }

    fun json(json: String, methodCount: Int) {
        json(TAG, json, methodCount)
    }

    /**
     * Formats the json content and print it
     *
     * @param json        the json content
     * @param methodCount number of the method that will be printed
     */
    @JvmOverloads
    fun json(tag: String, json: String, methodCount: Int = settings.methodCount) {
        validateMethodCount(methodCount)
        if (TextUtils.isEmpty(json)) {
            e(tag, "Empty/Null json content", methodCount)
            return
        }
        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                val message = jsonObject.toString(JSON_INDENT)
                e(tag, message, methodCount)
                return
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                val message = jsonArray.toString(JSON_INDENT)
                e(tag, message, methodCount)
            }
        } catch (e: JSONException) {
            e(
                tag, """
     ${e.cause!!.message}
     $json
     """.trimIndent(), methodCount
            )
        }
    }

    private fun log(
        logType: Int,
        tag: String,
        message: String,
        methodCount: Int,
        save2File: Boolean
    ) {
        var logType = logType
        if (settings.logLevel == LogLevel.NONE) {
            return
        }
        if (Build.BRAND.toLowerCase().contains("huawei")) {
            logType = Log.ERROR
        }
        logTopBorder(logType, tag)
        logHeaderContent(logType, tag, methodCount, save2File)

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        val bytes = message.toByteArray()
        val length = bytes.size
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(logType, tag)
            }
            logContent(logType, tag, message, save2File)
            logBottomBorder(logType, tag)
            return
        }
        if (methodCount > 0) {
            logDivider(logType, tag)
        }
        var i = 0
        while (i < length) {
            val count = Math.min(length - i, CHUNK_SIZE)
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(logType, tag, String(bytes, i, count), save2File)
            i += CHUNK_SIZE
        }
        logBottomBorder(logType, tag)
    }

    private fun logTopBorder(logType: Int, tag: String) {
        logChunk(logType, tag, TOP_BORDER)
    }

    private fun logHeaderContent(logType: Int, tag: String, methodCount: Int, save2File: Boolean) {
        val trace = Thread.currentThread().stackTrace
        if (settings.showThreadInfo) {
            logChunk(
                logType,
                tag,
                HORIZONTAL_DOUBLE_LINE.toString() + " Thread: " + Thread.currentThread().name
            )
            logDivider(logType, tag)
        }
    }

    private fun logBottomBorder(logType: Int, tag: String) {
        logChunk(logType, tag, BOTTOM_BORDER)
    }

    private fun logDivider(logType: Int, tag: String) {
        logChunk(logType, tag, MIDDLE_BORDER)
    }

    private fun logContent(logType: Int, tag: String, chunk: String, save2File: Boolean) {
        val lines = chunk.split(System.getProperty("line.separator").toRegex()).toTypedArray()
        for (line in lines) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE.toString() + " " + line)
            if (save2File) {
                saveLog2File(logType, tag, line)
            }
        }
    }

    private fun logChunk(logType: Int, tag: String, chunk: String) {
        val finalTag = formatTag(tag)
        when (logType) {
            Log.ERROR -> Log.e(finalTag, chunk)
            Log.INFO -> Log.i(finalTag, chunk)
            Log.VERBOSE -> Log.v(finalTag, chunk)
            Log.WARN -> Log.w(finalTag, chunk)
            Log.ASSERT -> Log.wtf(finalTag, chunk)
            Log.DEBUG -> Log.d(finalTag, chunk)
            else -> Log.d(finalTag, chunk)
        }
    }

    private fun getSimpleClassName(name: String): String {
        val lastIndex = name.lastIndexOf(".")
        return name.substring(lastIndex + 1)
    }

    private fun validateMethodCount(methodCount: Int) {
        check(!(methodCount < 0 || methodCount > MAX_METHOD_COUNT)) { "methodCount must be > 0 and < 5" }
    }

    private fun formatTag(tag: String): String {
        return if (!TextUtils.isEmpty(tag) && !TextUtils.equals(TAG, tag)) {
            TAG + "-" + tag
        } else TAG
    }

    private fun saveLog2File(logType: Int, tag: String, message: String) {
        if (logWriter == null) {
            openLogFile()
        }
        try {
            logWriter!!.write(buildFormatLog(logType, tag, message))
            logWriter!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * open local log file
     */
    fun openLogFile() {
        if (logFileName != null) {
            try {
                val logFile = File(logFileName)
                var isNew = false
                if (logFile.exists()) {
                    val reader = BufferedReader(FileReader(logFile))
                    val firstLine = reader.readLine()
                    val createTime = java.lang.Long.valueOf(firstLine)
                    reader.close()
                    //超过7天，删除log文件
                    if (System.currentTimeMillis() - createTime > 7 * 24 * 60 * 60 * 1000) {
                        logFile.delete()
                        isNew = true
                    }
                } else {
                    logFile.createNewFile()
                    isNew = true
                }
                logWriter = FileWriter(logFileName, true)
                if (isNew) {
                    logWriter!!.write(
                        """
                            ${System.currentTimeMillis()}
                            
                            """.trimIndent()
                    )
                    logWriter!!.flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * close local log file
     */
    fun closeLogFile() {
        if (logWriter != null) {
            try {
                logWriter!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * format save log
     *
     * @param logType
     * @param tag
     * @param message
     * @return
     */
    fun buildFormatLog(logType: Int, tag: String, message: String): String {
        val timeStamp = SimpleDateFormat(
            "MM-dd HH:mm:ss.SSS",
            Locale.getDefault()
        ).format(Date())
        val type: String
        type = when (logType) {
            Log.ERROR -> "E"
            Log.INFO -> "I"
            Log.VERBOSE -> "V"
            Log.WARN -> "W"
            Log.ASSERT -> "A"
            Log.DEBUG -> "D"
            else -> "D"
        }
        return """$timeStamp $type/${formatTag(tag)}: $message
"""
    }

    class Settings {
        var methodCount = 2
        var showThreadInfo = true
        var saveLog = false

        /**
         * Determines how logs will printed
         */
        var logLevel = LogLevel.FULL
        fun hideThreadInfo(): Settings {
            showThreadInfo = false
            return this
        }

        fun setSaveLog(saveLog: Boolean): Settings {
            this.saveLog = saveLog
            return this
        }

        fun setMethodCount(methodCount: Int): Settings {
            validateMethodCount(methodCount)
            this.methodCount = methodCount
            return this
        }

        fun setLogLevel(logLevel: LogLevel): Settings {
            this.logLevel = logLevel
            return this
        }
    }

    enum class LogLevel {
        FULL, NONE
    }
}
