package cn.j1angvei.cnbetareader.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cnbetareader.bean.RawReview;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.remote.response.ExposedResponse;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/22.
 */
public class ReviewConverter extends NewsConverter<Review> {

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
        return Observable.from(toList(json));
    }

    private Review convert(RawReview raw) {
        Review review = new Review();

        review.setCommentId(raw.getFromId());
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
