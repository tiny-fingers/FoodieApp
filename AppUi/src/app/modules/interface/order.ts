export interface Order {
  id: number;
  orderStatus: "INITIALIZE" | "IN_PREPARATION" | "IN_DELIVERY" | "DELIVERED" | "CANCELLED"
  orderItems: OrderItem[],
  estimatedDeliveryTime: string
  restaurantName?: string
  orderDate?: string
}

export interface OrderRequest {
  cartId: number;
  userId: string
}

export interface OrderItem {
  name: string;
  quantity: number;
}
