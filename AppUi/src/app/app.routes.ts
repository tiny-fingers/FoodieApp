import {Routes} from '@angular/router';
import {HomeComponent} from "./modules/home/home.component";
import {MenuItemComponent} from "./modules/menu-item/menu-item.component";
import {OrderStatusComponent} from "./modules/order-status/order-status.component";
import {CartPageComponent} from "./modules/cart-page/cart-page.component";
import {OrdersComponent} from "./modules/orders/orders.component";
import {LoginComponent} from "./modules/login/login.component";
import {SignupComponent} from "./modules/signup/signup.component";

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'restaurants/:id/menu', component: MenuItemComponent },
  { path: 'orderStatus/:id', component: OrderStatusComponent },
  { path: 'my-cart', component: CartPageComponent },
  { path: 'orders', component: OrdersComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
];
