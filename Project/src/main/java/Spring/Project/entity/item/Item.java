package Spring.Project.entity.item;

import Spring.Project.entity.base.BaseEntity;
import Spring.Project.status.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter
@Entity @ToString
@Table(name = "item")
public class Item extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ItemId")
    private Long id;
    @Column(nullable = false, length = 50)
    private String itemNm;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(nullable = false)
    private int StockNumber;
    @Lob @Column(nullable = false)
    private String itemDetail;
    private ItemSellStatus itemSellStatus;



}
