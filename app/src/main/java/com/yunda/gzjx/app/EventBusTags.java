package com.yunda.gzjx.app;

/**
 * ================================================
 * 放置 AndroidEventBus 的 Tag, 便于检索
 * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
 * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.5">EventBusTags wiki 官方文档</a>
 * Created by MVPArmsTemplate on 01/07/2019 17:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface EventBusTags {
    public static final String NEED_TO_LOGIN = "need_to_login";
    public static final String NEED_TO_REFRESH_MATERIAL_LIST = "need_to_refresh_material_list";
    public static final String NEED_TO_REFRESH_TICKET_LIST = "need_to_refresh_ticket_list";
}
