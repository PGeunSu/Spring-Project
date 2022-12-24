package Spring.Project.dto.order;

import Spring.Project.entity.order.Order;
import Spring.Project.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistoryDto {

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }

    public OrderHistoryDto(Order order){
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
