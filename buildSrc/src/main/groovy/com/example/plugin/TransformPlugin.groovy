package com.example.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

/**
 * author: created by wentaoKing
 * date: created in 2020/8/17
 * description:
 */
class TransformPlugin extends Transform {

  @Override
  String getName() {
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
    return TransformManager.PROJECT_ONLY
  }

  @Override
  boolean isIncremental() {
    return false
  }

  @Override
  void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
    def inputs = transformInvocation.inputs
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
