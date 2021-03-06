package ceuilisa.mirai.fragments;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;

import ceuilisa.mirai.activities.PlayListDetailActivity;
import ceuilisa.mirai.adapters.PlayListNodeAdapter;
import ceuilisa.mirai.network.RetrofitUtil;
import ceuilisa.mirai.nodejs.LoginResponse;
import ceuilisa.mirai.nodejs.PlayListResponse;
import ceuilisa.mirai.nodejs.PlaylistBean;
import ceuilisa.mirai.utils.Local;
import io.reactivex.Observable;

/**
 * 获取已登录用户自己的歌单（创建的+收藏的）
 */
public class FragmentMyPlayList extends BaseListFragment<PlayListResponse, PlayListNodeAdapter, PlaylistBean> {

    @Override
    Observable<PlayListResponse> initApi() {
        LoginResponse user = Local.getUser();
        return RetrofitUtil.getNodeApi().getMyPlayList(user.getProfile().getUserId(), PAGE_SIZE, allItems.size());
    }

    @Override
    String getToolbarTitle() {
        return "我的歌单";
    }

    @Override
    void initAdapter() {
        mAdapter = new PlayListNodeAdapter(allItems, mContext);
        mAdapter.setOnItemClickListener((view, position, viewType) -> {
            Intent intent = new Intent(mContext, PlayListDetailActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(mActivity, view, "sharedView");
            intent.putExtra("id", String.valueOf(allItems.get(position).getId()));
            intent.putExtra("name", allItems.get(position).getName());
            intent.putExtra("author", allItems.get(position).getCreator().getNickname());
            intent.putExtra("dataType", "歌单");
            intent.putExtra("coverImg", allItems.get(position).getCoverImgUrl());
            mContext.startActivity(intent, optionsCompat.toBundle());
        });
    }
}
