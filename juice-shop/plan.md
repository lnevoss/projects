in progress structure, update as we go

src/main/java/com/yourname/juiceshop/
├── JuiceShopApplication.java
├── controller/
│   ├── ProductController.java
│   └── OrderController.java
├── service/
│   ├── ProductService.java
│   └── OrderService.java
├── repository/
│   ├── ProductRepository.java
│   └── OrderRepository.java
├── model/            (or "entity")
│   ├── Product.java
│   ├── Order.java
│   └── OrderItem.java
├── dto/
│   ├── auth/   RegisterRequest, LoginRequest
│   ├── user/   UserResponse
│   └── order/  OrderRequest, OrderItemRequest, OrderResponse, OrderItemResponse
└── exception/
    └── ProductNotFoundException.java

src/main/resources/
├── application.properties
└── data.sql          (optional sample data)