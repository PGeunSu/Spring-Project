package Spring.Project.entity.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "item_img")
public class ItemImg {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ItemImg_id")
    private Long id;
    private String oriImgName;
    private String imgName;
    private String imgUrl;
    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    //업데이트 메서드
    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
