package com.tgt.weekly.publication.ssh2;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 14:52
 * @Description: To change this template use File | Settings | File Templates.
 **/
public interface LinuxConnect<T, K> {

    /**
     * 执行命令
     *
     * @param t
     * @return
     */
    boolean execScript(T t);

    /**
     * 下载
     *
     * @param t
     */
    void download(T t);


    /**
     * 生成新数据
     *
     */
    void generateData();

}
