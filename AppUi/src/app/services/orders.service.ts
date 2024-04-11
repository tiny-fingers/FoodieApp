import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Order, OrderRequest} from "../modules/interface/order";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  postOrder(order: OrderRequest) {
    const url = `${environment.SERVER_URL}/api/orders`;
    const token = this.authService.getLoginToken();
    const headers = new HttpHeaders({"Authorization": `Bearer ${token}`});
    return this.http.post<{ orderId : number}>(url, order, {headers, withCredentials: true});
  }

  getOrders(){
    const url = `${environment.SERVER_URL}/api/orders`;
    const token = this.authService.getLoginToken();
    const headers = new HttpHeaders({"Authorization": `Bearer ${token}`});
    return this.http.get<Order[]>(url, {headers})
  }

  getOrderStatus(orderId: number) {
    const url = `${environment.SERVER_URL}/api/orders/${orderId}`;
    const token = this.authService.getLoginToken();
    const headers = new HttpHeaders({"Authorization": `Bearer ${token}`});
    return this.http.get<Order>(url, {headers});
  }

  cancelOrder(orderId: number) {
    const url = `${environment.SERVER_URL}/api/orders/${orderId}?action=cancel`;
    const token = this.authService.getLoginToken();
    const headers = new HttpHeaders({"Authorization": `Bearer ${token}`});
    return this.http.post<void>(url, undefined, {headers});
  }
}
