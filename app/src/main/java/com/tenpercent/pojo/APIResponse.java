package com.tenpercent.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIResponse {


    public class HomeResponse{
        @SerializedName("slider")
        @Expose
        private List<Slider> slider = null;
        @SerializedName("offers")
        @Expose
        private List<Products> offers = null;
        @SerializedName("top_seled")
        @Expose
        private List<Products> topSeled = null;
        @SerializedName("recommended")
        @Expose
        private List<Products> recommended = null;

        public List<Slider> getSlider() {
            return slider;
        }

        public void setSlider(List<Slider> slider) {
            this.slider = slider;
        }

        public List<Products> getOffers() {
            return offers;
        }

        public void setOffers(List<Products> offers) {
            this.offers = offers;
        }

        public List<Products> getTopSeled() {
            return topSeled;
        }

        public void setTopSeled(List<Products> topSeled) {
            this.topSeled = topSeled;
        }

        public List<Products> getRecommended() {
            return recommended;
        }

        public void setRecommended(List<Products> recommended) {
            this.recommended = recommended;
        }

    }




}
