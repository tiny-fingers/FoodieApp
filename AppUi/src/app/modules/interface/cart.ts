import {CartItem} from "./cart-item";

export interface Cart {
  id: number;
  restaurantId: number;
  restaurantName: string;
  cartItems: CartItem[];
  totalPrice: number;
  userId: string
}
