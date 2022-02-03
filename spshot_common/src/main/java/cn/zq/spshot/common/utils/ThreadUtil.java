package cn.zq.spshot.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 华韵流风
 * @ClassName ThreadUtil
 * @Date 2022/1/17 17:19
 * @packageName cn.zq.spshot.common.utils
 * @Description TODO
 */
public class ThreadUtil {
    public static ExecutorService pool = Executors.newCachedThreadPool();
}
