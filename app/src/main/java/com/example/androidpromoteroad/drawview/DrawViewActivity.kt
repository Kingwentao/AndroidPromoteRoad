package com.example.androidpromoteroad.drawview

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.drawview.animation.ProvinceEvaluator
import com.example.androidpromoteroad.drawview.animation.provinces
import com.example.androidpromoteroad.utils.dp2px
import kotlinx.android.synthetic.main.activity_annotation_process.*
import kotlinx.android.synthetic.main.activity_draw_view.*

class DrawViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_view)
        //setContentView(R.layout.example_tag_layout)
        var markNum = 0
//        dashView.setOnClickListener {
//            dashView.markNum = markNum++
//            dashView.invalidate()
//        }

        //ViewPropertyAnimator 做动画: 只能针对特有的几个属性做设置，自定义属性就没办法了
        /*view.animate()
            .translationX(200f.dp2px)
            .translationY(100f.dp2px)
            .scaleY(2f)
            .scaleX(2f)
            .setStartDelay(1000)*/

        //ObjectAnimator： 可以对自定义属性做动画，不过一次只能操作一个属性
        /*val animate = ObjectAnimator.ofFloat(circleView,"mRadius",150f.dp2px)
        animate.startDelay = 1000
        animate.start()*/


        //翻动上页动画
        /* val bottomFlipAnimate = ObjectAnimator.ofFloat(flipView, "bottomFlip", 60f)
         bottomFlipAnimate.startDelay = 200
         bottomFlipAnimate.duration = 1000

         val rotateFlipAnimate = ObjectAnimator.ofFloat(flipView, "flipRotate", 270f)
         rotateFlipAnimate.startDelay = 200
         rotateFlipAnimate.duration = 3000

         val topFlipAnimate = ObjectAnimator.ofFloat(flipView, "topFlip", -60f)
         topFlipAnimate.startDelay = 200
         topFlipAnimate.duration = 1000

         //使用动画集，来设计上面的翻页动画
         val animateSet = AnimatorSet()
         animateSet.playSequentially(bottomFlipAnimate, rotateFlipAnimate, topFlipAnimate)
         animateSet.startDelay = 1000
         animateSet.start()*/


        //PropertyValuesHolder 可以对某个属性的动画做的更细，控制每一帧的效果，比如下面平移动画： 快-慢-快+回弹效果
        /*val transX = 100f.dp2px
        val keyFrame1 = Keyframe.ofFloat(0f, 0f * transX)
        val keyFrame2 = Keyframe.ofFloat(0.2f, 1.2f * transX)  //一下拉的更远，再拉回来就像回弹一样
        val keyFrame3 = Keyframe.ofFloat(0.8f, 0.6f * transX)
        val keyFrame4 = Keyframe.ofFloat(1f, 1f * transX)
        //把设计的帧数拼起来
        val frameAnimateHolder = PropertyValuesHolder.ofKeyframe(
            "translationX",
            keyFrame1,
            keyFrame2,
            keyFrame3,
            keyFrame4
        )
        val transAnimate = ObjectAnimator.ofPropertyValuesHolder(flipView, frameAnimateHolder)
        transAnimate.duration = 3000
        transAnimate.startDelay = 1000
        transAnimate.start()*/

        //作省份切换的动画: 使用自定义的ProvinceEvaluator()
//        val provinceAnimate =
//            ObjectAnimator.ofObject(provinceView, "province",
//                ProvinceEvaluator(), provinces.last())
//        provinceAnimate.startDelay = 1000
//        provinceAnimate.duration = 10000
//        provinceAnimate.start()

        //绘制动画的时候可以开启离屏缓冲，当然只有自带属性（像translation...）才可以享受带来的优化，
        // 自定义属性不要使用，反而会带来创建的额外开销，性能更差
        //provinceView.animate().translationX(100f).withLayer()

    }
}