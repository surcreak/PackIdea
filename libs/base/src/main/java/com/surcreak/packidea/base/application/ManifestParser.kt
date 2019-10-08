package com.surcreak.packidea.base.application

import android.content.Context
import android.content.pm.PackageManager

class ManifestParser(private val context: Context) {

    fun parse(): MutableList<IModuleConfig> {
        val modules = ArrayList<IModuleConfig>()
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData.get(key)) {
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("解析Application失败", e)
        }

        return modules
    }

    companion object {
        private const val MODULE_VALUE = "IModuleConfig"

        private fun parseModule(className: String): IModuleConfig {
            val clazz: Class<*>
            try {
                clazz = Class.forName(className)
            } catch (e: ClassNotFoundException) {
                throw IllegalArgumentException(e)
            }

            val module: Any
            try {
                module = clazz.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }

            return module as IModuleConfig
        }
    }
}