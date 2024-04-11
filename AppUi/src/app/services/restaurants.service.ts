import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Restaurant} from "../modules/interface/restaurant";
import {MenuItem} from "../modules/interface/menu-item";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RestaurantsService {

  constructor(private http: HttpClient) { }

  fetchRestaurant(searchTerm?: string)  {
   return this.http.get<Restaurant[]>(
     `${environment.SERVER_URL}/api/restaurants?search=` + searchTerm)
  }

  fetchRestaurantMenu(restaurantId?: number) {
    return this.http.get<{menu: MenuItem[]}>(
      `${environment.SERVER_URL}/api/restaurants/${restaurantId}/menu`
      // `${environment.SERVER_URL}/api/restaurants/${restaurantId}/menu`, { withCredentials: true }
    )
  }
}
