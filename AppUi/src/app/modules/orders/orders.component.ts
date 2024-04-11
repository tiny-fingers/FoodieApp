import {Component, Input} from '@angular/core';
import {Order} from '../interface/order';
import {Router} from "@angular/router";
import {Location, NgForOf, NgIf} from "@angular/common";
import {OrdersPipe} from "./orders.pipe";
import {OrdersService} from "../../services/orders.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [
    NgForOf,
    OrdersPipe,
    NgIf
  ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent {
  @Input() orders: Order[] = [];

  constructor(private router: Router,
              private orderService: OrdersService,
              private location: Location,
              private authService: AuthService) { }

  ngOnInit() {
    this.orderService.getOrders().subscribe({
      next: orders => {this.orders = orders}

    })
  }

  cancelOrder(id: number) {
    if (window.confirm("Click OK to confirm cancel order")) {
      this.orderService.cancelOrder(id).subscribe({
        next: data => {
          this.orderService.getOrders().subscribe({
            next: orders => {this.orders = orders}
          });
        },
      });
    }
  }
}
