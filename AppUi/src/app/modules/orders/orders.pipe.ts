import {Pipe, PipeTransform} from '@angular/core';
import {Order} from "../interface/order";

@Pipe({
  name: 'statusSortedOrders',
  standalone: true
})
export class OrdersPipe implements PipeTransform {

  transform(orders: Order[]): Order[] {
    return orders.sort((a, b) => {
      if (a.orderStatus === 'CANCELLED' && b.orderStatus !== 'CANCELLED') {
        return 1;
      } else if (a.orderStatus !== 'CANCELLED' && b.orderStatus === 'CANCELLED') {
        return -1;
      } else {
        return 0;
      }
    });
  }

}
