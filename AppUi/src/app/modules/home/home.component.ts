import {Component, Input} from '@angular/core';
import {RouterLink} from "@angular/router";
import {SearchBoxComponent} from "../search-box/search-box.component";
import {NgClass, NgForOf} from "@angular/common";
import {CartComponent} from "../cart/cart.component";
import {Restaurant} from "../interface/restaurant";
import {RestaurantsService} from "../../services/restaurants.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    SearchBoxComponent,
    NgForOf,
    CartComponent,
    RouterLink,
    NgClass
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  @Input() restaurants: Restaurant[] = [];
  hover = false;

  constructor(protected restaurantService: RestaurantsService) {
  }

  ngOnInit(){
    this.restaurantService.fetchRestaurant("").subscribe({
      next: (data: Restaurant[]) => {this.restaurants = data}
    })
  }

  searchRestaurants(searchTerm: string) {
    this.restaurantService.fetchRestaurant(searchTerm).subscribe({
      next: (data: Restaurant[]) => {this.restaurants = data}
    });
    console.log( "filterRestaurants " + searchTerm);
  }
}
