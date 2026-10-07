in progress structure, update as we go

src/main/java/io/github/Invoss/juice_shop/
│
├── JuiceShopApplication.java
│
├── config/
│   └── SecurityConfig.java
│
├── controller/
│   ├── AuthController.java
│   ├── ProductController.java
│   └── OrderController.java
│
├── dto/
│   ├── auth/
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   └── RegisterRequest.java
│   │
│   ├── product/
│   │   ├── ProductRequest.java
│   │   └── ProductResponse.java
│   │
│   └── order/
│       ├── OrderRequest.java
│       ├── OrderItemRequest.java
│       ├── OrderResponse.java
│       └── OrderItemResponse.java
│
├── exception/
│   ├── ProductNotFoundException.java
│   ├── OrderNotFoundException.java
│   ├── UserNotFoundException.java
│   ├── InsufficientStockException.java
│   └── GlobalExceptionHandler.java
│
├── model/
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Product.java
│   ├── User.java
│   └── Role.java
│
├── repository/
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
│
└── service/
    ├── AuthService.java
    ├── OrderService.java
    ├── ProductService.java
    └── UserService.java