package com.example.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.ImmutableSet

/**
 * author: created by wentaoKing
 * date: created in 2020/8/17
 * description:
 *  transForm具体：https://yutiantina.github.io/2019/04/24/%E6%B7%B1%E5%85%A5%E4%BA%86%E8%A7%A3TransformApi/
 */
class TransformPlugin extends Transform {

    @Override
    String getName() {
        //name会用于生成的task名中
        return "jwt"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        //如果是application注册的transform,
        // 通常情况下, 我们一般指定TransformManager.SCOPE_FULL_PROJECT;
        // 如果是library注册的transform, 我们只能指定TransformManager.PROJECT_ONLY，否则会报错
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 如果要使用自定义的Transform，一定要重写该方法，否则会使用父方法，而父方法默认实现为空，就会相当于你拦截了处理过程，
     * 但啥都不做，导致打包过程需要的文件丢失，导致打包失败
     * @param transformInvocation the invocation object containing the transform inputs.
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        def inputs = transformInvocation.inputs
        //需要输出的地方
        def outputProvider = transformInvocation.outputProvider
        inputs.each {
            it.jarInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
                println "Jar: ${it.file}, Dest: ${dest}"
                FileUtils.copyFile(it.file, dest)
            }
            it.directoryInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                println "Dir: ${it.file}, Dest: ${dest}"
                FileUtils.copyDirectory(it.file, dest)
            }
        }
    }
}
