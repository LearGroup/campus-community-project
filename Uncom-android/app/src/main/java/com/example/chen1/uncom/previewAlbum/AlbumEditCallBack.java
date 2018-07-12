package com.example.chen1.uncom.previewAlbum;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by chen1 on 2018/1/29.
 */

public interface AlbumEditCallBack {

    @Subscribe(threadMode = ThreadMode.MAIN)
    void albumResult(EventMessage eventMessage);
}
