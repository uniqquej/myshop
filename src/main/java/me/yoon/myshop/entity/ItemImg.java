package me.yoon.myshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "item_img")
public class ItemImg extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String representationImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName=oriImgName;
        this.imgName=imgName;
        this.imgUrl=imgUrl;
    }

}
