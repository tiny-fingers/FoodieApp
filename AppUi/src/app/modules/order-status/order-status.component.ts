import {Component, Input} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Order} from "../interface/order";
import {OrdersService} from "../../services/orders.service";

@Component({
  selector: 'app-order-status',
  standalone: true,
  imports: [],
  templateUrl: './order-status.component.html',
  styleUrl: './order-status.component.css'
})
export class OrderStatusComponent {
  @Input() order?: Order;
  id?: number;

  constructor(private route: ActivatedRoute,
              private orderService: OrdersService) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    this.orderService.getOrderStatus(this.id!)
      .subscribe({
        next: (order: Order) => {this.order  = order}
      })
  }
}
