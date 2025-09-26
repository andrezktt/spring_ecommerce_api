-- Este script garante a consistência dos dados de status de pedidos existentes.
UPDATE purchase_orders SET status = 'PENDING' WHERE status = 'PENDING';