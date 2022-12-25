package Spring.Project.repository.cartOrder;

import Spring.Project.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);
    //사용자가 소유한 장바구니를 memberId 를 사용해서 조회할 수 있도록

}