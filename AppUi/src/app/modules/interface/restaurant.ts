import {MenuItem} from "./menu-item";

export interface Restaurant {
  restaurantId: number;
  menu: MenuItem[];
  restaurantDetails: {
    name: string;
    restaurantAddress: string;
  };
}
