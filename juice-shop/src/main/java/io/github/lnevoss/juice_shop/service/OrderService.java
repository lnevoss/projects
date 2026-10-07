package io.github.lnevoss.juice_shop.service;

public class OrderService {
    
}


// package io.github.lnevoss.juice_shop.service;

// @Service
// @RequiredArgsConstructor
// public class OrderService {

//     private final OrderRepository orderRepository;
//     private final ProductRepository productRepository;

//     @Transactional
//     public Order placeOrder(User user, OrderRequest request) {
//         Order order = new Order();
//         order.setUser(user);
//         order.setCustomerName(user.getName());
//         order.setCustomerEmail(user.getEmail());
//         order.setCustomerPhone(user.getPhone());
//         order.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(3));

//         for (var line : request.items()) {
//             Product product = productRepository.findById(line.productId())
//                     .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//             order.addItem(new OrderItem(product, line.quantity()));  // both sides set here
//         }

//         return orderRepository.save(order);   // cascade saves the items too
//     }
// }

