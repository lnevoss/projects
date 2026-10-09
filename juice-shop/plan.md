# PLAN

> in progress structure, update as we go

```text
[x] - finished (presumably)
[~] - may be redundant
[/] - in progress
[ ] - #TODO

src/main/java/io/github/lnevoss/juice_shop/
│
├── [x] JuiceShopApplication.java
│
├── config/
│   └── [/] SecurityConfig.java
│
├── controller/
│   ├── [x] AuthController.java
│   ├── [ ] ProductController.java
│   ├── [ ] CartController.java
│   ├── [ ] NewsletterController.java
│   └── [x] NavigationController.java
│
├── dto/
│   ├── auth/
│   │   ├── [x] LoginRequest.java
│   │   ├── [x] LoginResponse.java
│   │   ├── [x] RegisterRequest.java
│   │   └── [x] RegisterResponse.java
│   │
│   ├── product/
│   │   ├── [x] ProductRequest.java
│   │   └── [x] ProductResponse.java
│   │
│   └── order/
│       ├── [x] OrderRequest.java
│       ├── [x] OrderItemRequest.java
│       ├── [x] OrderResponse.java
│       └── [x] OrderItemResponse.java
│
├── exception/
│   ├── [x] ProductNotFoundException.java
│   ├── [x] OrderNotFoundException.java
│   ├── [x] UserNotFoundException.java
│   ├── [x] InsufficientStockException.java
│   └── [/] GlobalExceptionHandler.java
│
├── model/
│   ├── [x] Order.java
│   ├── [x] OrderItem.java
│   ├── [x] Product.java
│   ├── [x] User.java
│   └── [x] Role.java
│
├── repository/
│   ├── [x] OrderRepository.java
│   ├── [x] ProductRepository.java
│   └── [x] UserRepository.java
│
└── service/
    ├── [/] AuthService.java
    ├── [/] OrderService.java
    ├── [~] ProductService.java
    └── [ ] UserService.java
```

## WIP

### Add Thymeleaf or another template generator for products

> Should be easy enough, right..?

### AuthService.java

> Session and JWT tokens

### OrderService.java

> Not sure yet, but may require more work later

### SecurityConfig.java

> comment out authentication before production, fix it later

### GlobalExceptionHandler.java

> update with new exception in the future, if needed
