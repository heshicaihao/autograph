package com.helloworldrn.module.autograph;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.heshicaihao.autograph.AutographView;

/***
 *                    .::::.
 *                  .::::::::.
 *                 :::::::::::
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 *
 *
 * Heshicaihao
 * 2019/7/17.
 */
public class AutographManager extends SimpleViewManager<AutographView> {

    /**
     * 设置js引用名
     * @return String
     */
    @Override
    public String getName() {
        return "MAutograph";
    }

    /**
     * 创建UI组件实例
     * @param reactContext
     * @return CircleView
     */
    @Override
    protected AutographView createViewInstance(ThemedReactContext reactContext) {
        return new AutographView(reactContext);
    }

    /**
     * 传输背景色参数
     * @param view
     * @param myheight
     */
    @ReactProp(name = "myheight")
    public void setHeight(AutographView view, Integer myheight) {
        view.setHeight(myheight);
    }

}
