package Spring.Project.dto.item;

import Spring.Project.status.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType; //상품 등록일
    private ItemSellStatus searchSellStatus; //상품 판매 상태
    private String searchBy; //상품명으로 조회할 때 어떤 유형으로 조회할 지
    private String searchQuery = ""; //조회 검색어를 저장할 변수
}
