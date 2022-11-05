package tech.noetzold.ecommerce.service;

import java.util.List;
import javax.transaction.Transactional;
import tech.noetzold.ecommerce.model.WishList;
import tech.noetzold.ecommerce.repository.WishListRepository;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(Integer userId) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}
