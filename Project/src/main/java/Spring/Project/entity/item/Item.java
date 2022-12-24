package Spring.Project.entity.item;

import Spring.Project.dto.item.ItemFormDto;
import Spring.Project.entity.base.BaseEntity;
import Spring.Project.exception.OutOfStockException;
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
    private int stockNumber;
    @Lob @Column(nullable = false)
    private String itemDetail;
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0 ){
            throw new OutOfStockException("상품의 재고가부 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    } //주문을 취소할 경우 주문 수량만큼 상품의 재고를 증가시키는 메서드를 구현



}
