package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension

/**
 * 自定义一个插件
 */
class MyTestPlugin implements Plugin<Project>{
    @Override
    void apply(Project target) {
        //创建扩展对象,名为 testExtension
        def pluginExtension = target.extensions.create('testExtension', PluginExtension)
        //要把真正执行的代码放在这里（等所有配置代码走回才会进入这里），否则，后面设置的都不会生效
        target.afterEvaluate {
            println "执行插件 ${pluginExtension.name}"
        }
        //
        def pluginTransform = new TransformPlugin()
        def baseExtension = target.extensions.getByType(BaseExtension)
        baseExtension.registerTransform(pluginTransform)
    }
}