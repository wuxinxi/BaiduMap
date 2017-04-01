package com.wxx.map.mvp;

import com.wxx.map.bean.ListData;

import java.util.List;

/**
 * 作者：Tangren_ on 2017/4/1 11:28.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public interface OnLoadResult {
    void onResult(int code, int total, int page, List<ListData.ContentsBean> list);
}
