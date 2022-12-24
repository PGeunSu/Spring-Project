package Spring.Project.dto.item;

import Spring.Project.entity.item.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String repImgYn;
    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
