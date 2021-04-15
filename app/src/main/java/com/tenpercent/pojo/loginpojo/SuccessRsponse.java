package com.tenpercent.pojo.loginpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tenpercent.pojo.rigetserPojo.ModelRegster;

public class SuccessRsponse {


public class loginRespons{

    @SerializedName("success")
    @Expose
    private LoginModel success;

    public LoginModel getSuccess() {
        return success;
    }

    public void setSuccess(LoginModel success) {
        this.success = success;
    }
}
    public class loginregister{

        @SerializedName("success")
        @Expose
        private ModelRegster success;

        public ModelRegster getSuccess() {
            return success;
        }

        public void setSuccess(ModelRegster success) {
            this.success = success;
        }
    }

}

