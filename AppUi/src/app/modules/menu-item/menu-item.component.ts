import {Component} from '@angular/core';
import {NgForOf} from "@angular/common";
import {MenuItem} from "../interface/menu-item";
import {ActivatedRoute} from "@angular/router";
import {CartComponent} from "../cart/cart.component";
import {CartService} from "../../services/cart.service";
import {RestaurantsService} from "../../services/restaurants.service";

@Component({
  selector: 'app-menu-item',
  standalone: true,
  imports: [
    NgForOf,
    CartComponent
  ],
  templateUrl: './menu-item.component.html',
  styleUrl: './menu-item.component.css'
})
export class MenuItemComponent {
  menuItems: MenuItem[] = []
  restaurantId: number | undefined;

  constructor(private route: ActivatedRoute, private cartService: CartService, private restaurantService: RestaurantsService) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.restaurantId = params['id'];
    })
    this.restaurantService.fetchRestaurantMenu(this.restaurantId).subscribe({
      next: data => {this.menuItems = data.menu.map(menuItem => ({
        ...menuItem,
        restaurantId: this.restaurantId!
      }))},
      error: err => {console.error(err)}
    })
  }

  addToCart(item: MenuItem) {
    this.cartService.addToCartItem({...item, menuItemId: item.id, unitPrice: item.price, quantity: 1});
  }
}
