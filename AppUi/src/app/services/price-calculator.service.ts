import {Injectable} from '@angular/core';
import {CurrencyPipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class PriceCalculatorService {

  constructor(private currencyPipe: CurrencyPipe) { }

  calculatePrice(unitPrice: number, quantity: number) {
    return quantity * unitPrice;
    // return this.currencyPipe.transform(quantity * unitPrice, "USD", "symbol", '1.2-2');
  }
}
