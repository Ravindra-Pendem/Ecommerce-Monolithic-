package com.app.ecom.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom.dto.CartRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
	
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final CartItemRepository cartItemRepository;

	public boolean addToCart(String userId, CartRequest request) {
		
		Optional<Product> productOpt = productRepository.findById(request.getProductId());
		if(productOpt.isEmpty())
			return false;
		
		Product product = productOpt.get();
		if(product.getStockQuantity() < request.getQuantity())
			return false;
		
		Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
		if(userOpt.isEmpty())
			return false;
		
		User user = userOpt.get();
		
		CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product); 
		if(existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
			existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
			cartItemRepository.save(existingCartItem);
		}
		else {
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(request.getQuantity());
			cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
			cartItem.setProduct(product);
			cartItem.setUser(user);
			cartItemRepository.save(cartItem);
		}
		
		return true;
	}

	public boolean deleteItemFromCart(String userId, Long productId) {
		
		Optional<Product> productOpt = productRepository.findById(productId);
		if(productOpt.isEmpty())
			return false;
		
		Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
		if(userOpt.isEmpty())
			return false;
		
		if(productOpt.isPresent() && userOpt.isPresent()) {
		cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
		return true;
		}
		
		return false;
	}

	public List<CartItem> getAllProductsFromCart(String userId) {
		
		return userRepository.findById(Long.valueOf(userId))
							.map(cartItemRepository::findByUser)
							.orElseGet(List::of);
	}

	public void clearCart(String userId) {
		userRepository.findById(Long.valueOf(userId)).ifPresent(user ->
					cartItemRepository.deleteByUser(user)
				);
	}

}
