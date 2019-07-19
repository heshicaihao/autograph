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
     *
     * @return String
     */
    @Override
    public String getName() {
        return "MAutograph";
    }

    /**
     * 创建UI组件实例
     *
     * @param reactContext
     * @return CircleView
     */
    @Override
    protected AutographView createViewInstance(ThemedReactContext reactContext) {
        return new AutographView(reactContext);
    }

    /**
     * 传输背景色参数
     *
     * @param view
     * @param myheight
     */
    @ReactProp(name = "myheight")
    public void seMytHeight(AutographView view, Integer myheight) {
        view.setMyHeight(myheight);
    }


    /**
     * 设置确认按钮文字
     *
     * @param text
     */
    @ReactProp(name = "confirmbtntext")
    public void setConfirmBtnText(AutographView view,String text) {
        view.setConfirmBtnText(text);
    }

    /**
     * 设置确认按钮文字大小
     *
     * @param view
     * @param size
     */
    @ReactProp(name = "confirmbtntextsize")
    public void setConfirmBtnTextSize(AutographView view, Integer size) {
        view.setConfirmBtnTextSize(size);
    }

    /**
     * 设置标题文字大小
     *
     * @param view
     * @param size
     */
    @ReactProp(name = "titletextsize")
    public void setTitleTextSize(AutographView view, Integer size) {
        view.setTitleTextSize(size);
    }

    /**
     * 设置清空图标大小
     *
     * @param view
     * @param size
     */
    @ReactProp(name = "cleaniconsize")
    public void setCleanIconSize(AutographView view, Integer size) {
        view.setCleanIconSize(size);
    }

    /**
     * 设置清空文字大小
     *
     * @param view
     * @param size
     */
    @ReactProp(name = "cleantextsize")
    public void setCleanTextSize(AutographView view, Integer size) {
        view.setCleanTextSize(size);
    }

    /**
     * 传Token
     *
     * @param view
     * @param token
     */
    @ReactProp(name = "token")
    public void setToken(AutographView view, String token) {
        view.setToken(token);
    }

    /**
     * 传type
     *
     * @param view
     * @param type
     */
    @ReactProp(name = "type")
    public void setType(AutographView view, String type) {
        view.setType(type);
    }

    /**
     * 传workNo
     *
     * @param view
     * @param workNo
     */
    @ReactProp(name = "workno")
    public void setWorkNo(AutographView view, String workNo) {
        view.setWorkNo(workNo);
    }


}
