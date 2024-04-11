import {Component, Input} from '@angular/core';
import {CurrencyPipe, NgForOf, NgIf} from "@angular/common";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {Cart} from "../interface/cart";
import {OrderRequest} from "../interface/order";
import {CartItem} from "../interface/cart-item";
import {CartService} from "../../services/cart.service";
import {PriceCalculatorService} from "../../services/price-calculator.service";
import {OrdersService} from "../../services/orders.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    CurrencyPipe
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  @Input() cartItems: CartItem[] = [];
  @Input() totalPrice: number | undefined;
  @Input() restaurantName: string | undefined;
  restaurantId: number = 0;
  cart: Cart = {
    userId: '',
    id: 0,
    restaurantId: 0,
    restaurantName: "",
    totalPrice: 0,
    cartItems: []}

  private subscription?: Subscription;

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.restaurantId = params['id'];
    })

    this.cartService.fetchOrInitCart(this.restaurantId);

    this.subscription = this.cartService.cart$.subscribe(
      {
        next: cart => {
          this.cart = cart;
          this.restaurantName = cart.restaurantName;
          this.cartItems = cart.cartItems;
          this.totalPrice = cart.totalPrice;
        },
      });
  }

  ngAfterViewInit() {
    this.cartService.cart$.subscribe(
      {
        next: cart => {
          this.cart = cart;
          this.cartItems = cart.cartItems.filter((item: CartItem) => !!item.quantity);
          this.totalPrice = cart.totalPrice;
        }
      });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  constructor(private cartService: CartService,
              private priceCalculatorService: PriceCalculatorService,
              private ordersService: OrdersService,
              private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private currencyPipe: CurrencyPipe) {
  }

  incrementQuantity(itemId: number) {
    const item = this.cartItems.find(i => i.menuItemId === itemId);
    if (item) {
      item.quantity++;
    }
    const updatedCart: Cart = ({
      ...this.cart,
      cartItems: this.cartItems.filter(i => i.quantity > 0)
    })
    this.cartService.updateCart(updatedCart);
  }

  decrementQuantity(itemId: any) {
    const item = this.cartItems.find(i => i.menuItemId === itemId);
    if (item && item.quantity > 0) {
      item.quantity--;
    }
    const updatedCart: Cart = ({
      ...this.cart,
      cartItems: this.cartItems.filter(i => i.quantity > 0)
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
        })
    }
  }

  protected readonly confirm = confirm;
}
