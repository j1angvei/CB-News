package cn.j1angvei.cbnews.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.RawReview;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.data.remote.response.ExposedResponse;
import cn.j1angvei.cbnews.util.ApiUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/22.
 */
@Singleton
public class ReviewConverter extends NewsConverter<Review> {
    @Inject
    public ReviewConverter(Gson gson) {
        super(gson);
    }

    @Override
    public Review to(String json) {
        return null;
    }

    @Override
    public List<Review> toList(String jsonp) {
        String json = ApiUtil.removeJsonpWrapper(jsonp);
        ExposedResponse<RawReview> response = mGson.fromJson(json, new TypeToken<ExposedResponse<RawReview>>() {
        }.getType());
        List<RawReview> rawReviews = response.getResult();
        List<Review> reviews = new ArrayList<>();
        for (RawReview raw : rawReviews) {
            reviews.add(convert(raw));
        }
        return reviews;
    }

    @Override
    public Observable<Review> toObservable(String json) {
        List<Review> reviews = toList(json);
        if (reviews == null) {
            return Observable.empty();
        }
        return Observable.from(reviews);
    }

    private Review convert(RawReview raw) {
        Review review = new Review();

        review.setTid(raw.getFromId());
        review.setComment(raw.getTitle());
        String description = raw.getDescription();

        String location = description.replaceFirst("^.+<strong>", "").replaceAll("</strong>.*$", "");
        review.setLocation(location);

        String sid = description.replaceFirst("^.*<a href=\\\"/articles/", "").replaceAll("\\.html?\\\" target=\\\"_blank\\\">.*$", "");
        review.setSid(sid);

        String articleTitle = description.replaceFirst("^.*\\.htm\\\" target=\\\"_blank\\\">", "").replaceAll("</a>.*$", "");
        review.setTitle(articleTitle);
        return review;
    }
}
