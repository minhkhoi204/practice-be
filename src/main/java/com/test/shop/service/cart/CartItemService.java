package com.test.shop.service.cart;

import com.test.shop.exceptions.ResourceNotFoundException;
import com.test.shop.model.Cart;
import com.test.shop.model.CartItem;
import com.test.shop.model.Product;
import com.test.shop.repository.CartItemRepository;
import com.test.shop.repository.CartRepository;
import com.test.shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()// check if products existed
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId)) // compare id
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) { // new cart, never been saved
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity); // plus quantity
        }
        cartItem.setTotalPrice(); // recalculate total price
        cart.addItem(cartItem); // ensure cartItem in cart list
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId); // get cart through cartID
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId); //take cart by id
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId)) //filter the item with input id
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity); // update new quantity
                    item.setUnitPrice(item.getProduct().getPrice()); //get product's price and set to unitprice
                    item.setTotalPrice(); //calculate total cartitem(unitprice*quantity)
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // calculate total amount

        cart.setTotalAmount(totalAmount); // set total amount cart again
        cartRepository.save(cart); // save
    }

    @Override //get accessory from controller
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId); // call cartservice to get cart from db base on id
        return cart.getItems() // get list cartitem in cart
                .stream() // begin to handle list
                .filter(item -> item.getProduct().getId().equals(productId)) // pick cartitems have id equals to input productid
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
