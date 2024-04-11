import {MenuItem} from "./menu-item";

export interface CartItem extends MenuItem {
  menuItemId: number;
  name: string;
  quantity: number;
  unitPrice: number;
}
