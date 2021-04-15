package com.tenpercent.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tenpercent.roomdatabase.DateConverter;


    @Entity(tableName = "ProductCart")
    @TypeConverters({DateConverter.class})
    public class ProductCart implements Parcelable {

        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        @Expose
        private Integer id;

        @ColumnInfo(name= "name")
        @SerializedName("name")
        @Expose
        private String name;

        @ColumnInfo(name= "desc")
        @SerializedName("desc")
        @Expose
        private String desc;

        @ColumnInfo(name= "image")
        @SerializedName("image")
        @Expose
        private String image;

        @ColumnInfo(name= "categoryId")
        @SerializedName("category_id")
        @Expose
        private Integer categoryId;

        @ColumnInfo(name= "sellerId")
        @SerializedName("seller_id")
        @Expose
        private Integer sellerId;

        @ColumnInfo(name= "order_count")
        @SerializedName("order_count")
        @Expose
        private Integer orderCount;

        @ColumnInfo(name= "rate")
        @SerializedName("rate")
        @Expose
        private Integer rate;

        @ColumnInfo(name= "price")
        @SerializedName("price")
        @Expose
        private Integer price;
        @ColumnInfo(name= "discount")
        @SerializedName("discount")
        @Expose
        private Integer discount;


        public ProductCart() {
        }

        public @Ignore ProductCart(Integer id, String name, String desc, String image,
                           Integer categoryId, Integer sellerId, Integer orderCount, Integer rate, Integer price, Integer discount) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.image = image;
            this.categoryId = categoryId;
            this.sellerId = sellerId;
            this.orderCount = orderCount;
            this.rate = rate;
            this.price = price;
            this.discount = discount;
        }

        protected ProductCart(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            name = in.readString();
            desc = in.readString();
            image = in.readString();
            if (in.readByte() == 0) {
                categoryId = null;
            } else {
                categoryId = in.readInt();
            }
            if (in.readByte() == 0) {
                sellerId = null;
            } else {
                sellerId = in.readInt();
            }
            if (in.readByte() == 0) {
                orderCount = null;
            } else {
                orderCount = in.readInt();
            }
            if (in.readByte() == 0) {
                rate = null;
            } else {
                rate = in.readInt();
            }
            if (in.readByte() == 0) {
                price = null;
            } else {
                price = in.readInt();
            }
            if (in.readByte() == 0) {
                discount = null;
            } else {
                discount = in.readInt();
            }
        }

        public static final Creator<ProductCart> CREATOR = new Creator<ProductCart>() {
            @Override
            public ProductCart createFromParcel(Parcel in) {
                return new ProductCart(in);
            }

            @Override
            public ProductCart[] newArray(int size) {
                return new ProductCart[size];
            }
        };

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getSellerId() {
            return sellerId;
        }

        public void setSellerId(Integer sellerId) {
            this.sellerId = sellerId;
        }

        public Integer getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(Integer orderCount) {
            this.orderCount = orderCount;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            dest.writeString(name);
            dest.writeString(desc);
            dest.writeString(image);
            if (categoryId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(categoryId);
            }
            if (sellerId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(sellerId);
            }
            if (orderCount == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(orderCount);
            }
            if (rate == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(rate);
            }
            if (price == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(price);
            }
            if (discount == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(discount);
            }
        }
    }
