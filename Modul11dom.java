classDiagram
    class User {
        <<abstract>>
        +int id
        +String name
        +String email
        +register()
        +login()
    }
    class Client {
        +String address
        +historyOrders
    }
    class Admin {
        +manageProducts()
        +logActions()
    }
    User <|-- Client
    User <|-- Admin

    class Product {
        +int id
        +String title
        +double price
        +int stockQuantity
        +update()
    }

    class Order {
        +int id
        +String status
        +double totalAmount
        +placeOrder()
        +pay()
    }

    class Payment {
        +int id
        +String type
        +processPayment()
    }

    class Delivery {
        +int id
        +String status
        +track()
    }

    Client "1" -- "*" Order : жасайды
    Order "*" -- "*" Product : қамтиды
    Order "1" -- "1" Delivery : жеткізіледі
    Order "1" -- "1" Payment : төленеді
componentDiagram
    [Frontend App] <<Component>> as UI
    [Backend Server] <<Component>> as BE
    database "Database" as DB
    [Warehouse Module] <<Component>> as WH
    [Courier Integration] <<Component>> as Courier
    [Notification System] <<Service>> as Notify

    UI --( REST_API
    REST_API -- BE
    
    BE --( SQL_Interface
    SQL_Interface -- DB
    
    BE --( Inventory_API
    Inventory_API -- WH
    
    BE --( Courier_API
    Courier_API -- Courier
    
    BE ..> Notify : "Trigger SMS/Email"
