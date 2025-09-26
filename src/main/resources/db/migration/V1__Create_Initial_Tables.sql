-- Cria a tabela de produtos
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024),
    price NUMERIC(19, 2) NOT NULL,
    stock_quantity INTEGER NOT NULL
);

-- Cria a tabela de clientes
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    role VARCHAR(255) NOT NULL
);

-- Cria a tabela de pedidos
CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date TIMESTAMP NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY(customer_id) REFERENCES customers(id)
);

-- Cria a tabela de itens do pedido
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(19, 2) NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY(order_id) REFERENCES purchase_orders(id),
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES products(id)
);