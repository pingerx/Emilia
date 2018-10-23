package ceuilisa.mirai.activities;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ceuilisa.mirai.R;
import ceuilisa.mirai.adapters.PlayListAdapter;
import ceuilisa.mirai.network.RetrofitUtil;
import ceuilisa.mirai.response.PlayListTitleResponse;
import ceuilisa.mirai.utils.Common;
import ceuilisa.mirai.utils.Constant;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class PlayListActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    @Override
    void initLayout() {
        mLayoutID = R.layout.activity_play_list;
    }

    @Override
    void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        mProgressBar = findViewById(R.id.progress);
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    void initData() {
        RetrofitUtil.getTengkoaApi().getAllPlayList(Constant.USER_NAME)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlayListTitleResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(PlayListTitleResponse playListTitleResponse) {
                        List<PlayListTitleResponse.Result.PlayList> mPlayLists =
                                new ArrayList<>(playListTitleResponse.result.playlists);
                        Common.showLog(playListTitleResponse.result.playlists.size());
                        PlayListAdapter mAdapter = new PlayListAdapter(mPlayLists, mContext);
                        mAdapter.setOnItemClickListener((view, position, viewType) -> {
                            Intent intent = new Intent(mContext, PlayListDetailActivity.class);
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                    .makeSceneTransitionAnimation(mActivity, view, "sharedView");
                            intent.putExtra("id", mPlayLists.get(position).id);
                            intent.putExtra("name", mPlayLists.get(position).name);
                            intent.putExtra("coverImg", mPlayLists.get(position).coverImgUrl);
                            mContext.startActivity(intent, optionsCompat.toBundle());
                        });
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(mAdapter));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
