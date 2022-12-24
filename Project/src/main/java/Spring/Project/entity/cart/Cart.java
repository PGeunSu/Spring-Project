package Spring.Project.entity.cart;

import Spring.Project.entity.Member;
import Spring.Project.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @ToString
@Getter @Setter
@Table(name = "cart")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cartId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public static Cart creatCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }

}
