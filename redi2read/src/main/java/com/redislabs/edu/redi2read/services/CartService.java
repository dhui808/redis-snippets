package com.redislabs.edu.redi2read.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.google.gson.Gson;
import com.redislabs.edu.redi2read.models.Book;
import com.redislabs.edu.redi2read.models.Cart;
import com.redislabs.edu.redi2read.models.CartItem;
import com.redislabs.edu.redi2read.models.User;
import com.redislabs.edu.redi2read.repositories.BookRepository;
import com.redislabs.edu.redi2read.repositories.CartRepository;
import com.redislabs.edu.redi2read.repositories.UserRepository;
//import com.redislabs.modules.rejson.JReJSON;
//import com.redislabs.modules.rejson.Path;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.json.Path2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  //private JReJSON redisJson = new JReJSON();

  //Path cartItemsPath = Path.of(".cartItems");

  private JedisPooled client = new JedisPooled("localhost", 6379);
  Path2 cartItemsPath = Path2.of(".cartItems");
  Path path = Path.of(".cartItems");
  
  public Cart get(String id) {
    return cartRepository.findById(id).get();
  }

  public void addToCart(String id, CartItem item) {
    Optional<Cart> cartFinder = cartRepository.findById(id);
    if (cartFinder.isPresent()) {
      Optional<Book> book = bookRepository.findById(item.getIsbn());
      if (book.isPresent()) {
        String cartKey = CartRepository.getKey(id);
        item.setPrice(book.get().getPrice());
        //redisJson.arrAppend(cartKey, cartItemsPath, item);
        client.jsonArrAppend(cartKey, cartItemsPath, item);
      }
    }
  }

  public void removeFromCart(String id, String isbn) {
    Optional<Cart> cartFinder = cartRepository.findById(id);
    if (cartFinder.isPresent()) {
      Cart cart = cartFinder.get();
      String cartKey = CartRepository.getKey(cart.getId());
      List<CartItem> cartItems = new ArrayList<CartItem>(cart.getCartItems());
      OptionalInt cartItemIndex =  IntStream.range(0, cartItems.size()).filter(i -> cartItems.get((int) i).getIsbn().equals(isbn)).findFirst();
      if (cartItemIndex.isPresent()) {
        //redisJson.arrPop(cartKey, CartItem.class, cartItemsPath, cartItemIndex.getAsLong());
    	client.jsonArrPop(cartKey, CartItem.class, path, cartItemIndex.getAsInt());
      }
    }
  }

  public void checkout(String id) {
    Cart cart = cartRepository.findById(id).get();
    User user = userRepository.findById(cart.getUserId()).get();
    cart.getCartItems().forEach(cartItem -> {
      Book book = bookRepository.findById(cartItem.getIsbn()).get();
      user.addBook(book);
    });
    userRepository.save(user);
    // cartRepository.delete(cart);
  }
}
