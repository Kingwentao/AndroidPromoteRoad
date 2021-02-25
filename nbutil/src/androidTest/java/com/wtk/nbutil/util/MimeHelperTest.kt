package com.wtk.nbutil.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cvte.dss.helper.MimeHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description:
 */

@RunWith(AndroidJUnit4::class)
class MimeHelperTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val mMimeHelper = MimeHelper(appContext)
    private val pdfMimeType = "application/pdf"
    private val pngMimeType = "image/png"

    @Test
    fun testMimeType() {
        val type = mMimeHelper.getMimeType("png")
        Assert.assertEquals(pngMimeType, type)

        val pdfType = mMimeHelper.getMimeType("pdf")
        Assert.assertEquals(pdfMimeType, pdfType)
    }

    @Test
    fun testExtension() {
        val pdfExtension = mMimeHelper.getExtension(pdfMimeType)
        Assert.assertEquals(pdfExtension,"pdf")
    }

}