import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs";
import {Cart} from "../modules/interface/cart";
import {CartItem} from "../modules/interface/cart-item";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) {
  }

  private cartSubject = new BehaviorSubject<Cart>({
    userId: '',
    id: 0,
    restaurantId: 0,
    restaurantName: "",
    totalPrice: 0,
    cartItems: []
  });
  cart$ = this.cartSubject.asObservable();

  fetchCart() {
    return this.http.get<Cart>(`${environment.SERVER_URL}/api/cart`, {withCredentials: true})
      .subscribe({
        next: cart => {
          console.log("Fetch Cart");
          this.cartSubject.next(cart);},
      })
  }

  fetchOrInitCart(restaurantId: number) {
    return this.http.get<Cart>(`${environment.SERVER_URL}/api/cart`, {withCredentials: true})
      .subscribe({
        next: (cart: Cart) => {
          if (cart.restaurantId != restaurantId) {
            alert("You have changed restaurant. Consider placing the order before adding items from another restaurant")
          }
          this.cartSubject.next(cart);
          if (!cart) {
            this.initCart(restaurantId);
          }
        },
        error: (err: Error) => {
          this.initCart(restaurantId);
        }
      });
  }

  initCart(restaurantId: number) {
    return this.http.get<Cart>(`${environment.SERVER_URL}/api/cart/init?restaurantId=${restaurantId}`,
      {withCredentials: true})
      .subscribe({
        next: (cart: Cart) => {this.cartSubject.next(cart)}
      })
      ;
  }

  resetCart() {
    this.cartSubject.next({
      userId: '',
      id: 0,
      restaurantId: 0,
      restaurantName: "",
      totalPrice: 0,
      cartItems: []
    });
  }

  newCart(restaurantId: number) {
    return this.http.get<Cart>(`${environment.SERVER_URL}/api/cart/init?restaurantId=${restaurantId}`,
      {withCredentials: true});

  }

  addToCartItem(item: CartItem) {
    if (item.restaurantId != this.cartSubject.getValue().restaurantId) {
      if (confirm("Do you wish to replace current cart with new item?")) {
        this.newCart(item.restaurantId)
          .subscribe({
          next: (cart: Cart) => {
            const updatedCart = ({
              ...cart,
              cartItems: [item]
            })
            this.updateCart(updatedCart)
            this.cartSubject.next(updatedCart)
          }
        })
      }
    } else {
      const updatedCart: Cart =
        ({
          ...this.cartSubject.getValue(),
          cartItems: [
            ...this.cartSubject.value.cartItems,
            item
          ]
        });
      this.updateCart(updatedCart);
      this.cartSubject.next(updatedCart);
    }
  }

  updateCart(data: Cart) {
    const url = `${environment.SERVER_URL}/api/cart`;
    return this.http.post<Cart>(url, data, {withCredentials: true})
      .subscribe({
        next: (cart: Cart) => {this.cartSubject.next(cart)},
        error: (error) => console.error(error)
      });
  }

}
