import {Component, Input} from '@angular/core';
import {Cart} from "../interface/cart";
import {CurrencyPipe, NgForOf, NgIf} from "@angular/common";
import {Subscription} from "rxjs";
import {OrderRequest} from "../interface/order";
import {Router} from "@angular/router";
import {CartService} from "../../services/cart.service";
import {OrdersService} from "../../services/orders.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-cart-page',
  standalone: true,
  imports: [
    CurrencyPipe,
    NgIf,
    NgForOf
  ],
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.css'
})
export class CartPageComponent {
  @Input() cart!: Cart;
  private subscription?: Subscription;
  constructor(private cartService: CartService,
              private ordersService: OrdersService,
              private authService: AuthService,
              private router: Router) {}

  ngOnInit() {
    this.cartService.fetchCart();

    this.subscription = this.cartService.cart$.subscribe(
      {
        next: cart => {
          this.cart = cart;
        },
      });
  }

  ngAfterViewInit() {
    this.subscription = this.cartService.cart$.subscribe(
      {
        next: cart => {
          this.cart = cart;
        },
      });
  }

  incrementQuantity(itemId: number) {
    const cartItems = this.cart?.cartItems ?? [];
    const item = cartItems.find(i => i.menuItemId === itemId);
    if (item) {
      item.quantity++;
    }
    const updatedCart: Cart = ({
      ...this.cart,
      cartItems: this.cart!.cartItems.filter(i => i.quantity > 0)
    })
    this.cartService.updateCart(updatedCart);
  }

  decrementQuantity(itemId: any) {
    const cartItems = this.cart?.cartItems ?? [];
    const item = cartItems.find(i => i.menuItemId === itemId);
    if (item && item.quantity > 0) {
      item.quantity--;
    }
    const updatedCart: Cart = ({
      ...this.cart,
      cartItems: cartItems.filter(i => i.quantity > 0)
    })
    this.cartService.updateCart(updatedCart);
  }

  handleCheckOut() {
    const order: OrderRequest = {
      cartId: this.cart!.id,
      userId: this.cart!.userId,
    }
    if (!this.authService.isLoggedIn()) {
      void this.router.navigate(['/login']);
    } else {
      this.ordersService
        .postOrder(order)
        .subscribe({
          next: (res) => {
            this.cartService.resetCart()
            void this.router.navigate(['/orderStatus', res.orderId])
          },
          error: (error) => console.log(error)
        });
    }
  }

  goToRestaurant(restaurantId: number) {
    void this.router.navigate(['restaurants', restaurantId, 'menu'])
  }

  toHomepage() {
    void this.router.navigate(['/']);
  }
}
